package eu.eventstorm.samples.ex005;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Connection;
import java.sql.Statement;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eu.eventstorm.sql.Database;
import eu.eventstorm.sql.Dialect;
import eu.eventstorm.sql.Transaction;
import eu.eventstorm.sql.impl.DatabaseBuilder;
import eu.eventstorm.sql.impl.TransactionManagerImpl;

class JoinTableTest {

	private JdbcConnectionPool ds;
	private Database database;

	@BeforeEach
	void before() throws Exception {
		ds = JdbcConnectionPool.create("jdbc:h2:mem:test;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1", "sa", "");
		database = DatabaseBuilder.from(Dialect.Name.H2)
        		.withTransactionManager(new TransactionManagerImpl(ds))
        		.withModule(new Module("ex005", ""))
        		.build();
		Flyway flyway = Flyway.configure().dataSource(ds).load();
		flyway.migrate();
	}

	@AfterEach
	void after() throws Exception {
		try (Connection connection = ds.getConnection()) {
			try (Statement st = connection.createStatement()) {
				st.execute("shutdown");
			}
		}
		ds.dispose();
	}

	@Test
	void test001() throws Exception {

		Role r1 = Factory.newRole();
		r1.setId(1);
		r1.setName("ROLE_ADMIN");

		Role r2 = Factory.newRole();
		r2.setId(2);
		r2.setName("ROLE_USER");

		Resource res1 = Factory.newResource();
		res1.setId(1);
		res1.setName("/api/1.0/{id}");

		AbstractRoleRepository repository = new AbstractRoleRepository(database) {
        };

        AbstractResourceRepository resourceRepository = new AbstractResourceRepository(database) {
        };

        RoleResourceRepository roleResourceRepository = new RoleResourceRepository(database);

        try (Transaction tx = database.transactionManager().newTransactionReadWrite()) {
        	repository.insert(r1);
        	repository.insert(r2);
        	resourceRepository.insert(res1);
        	roleResourceRepository.link(r1, res1);
        	tx.commit();
        }

        try (Transaction tx = database.transactionManager().newTransactionReadOnly()) {
        	RoleResource roleResource = roleResourceRepository.findById(1, 1);
        	assertNotNull(roleResource);
        }
        
        try (Transaction tx = database.transactionManager().newTransactionReadWrite()) {
        	roleResourceRepository.unlink(r1, res1);
        	tx.commit();
        }

        try (Transaction tx = database.transactionManager().newTransactionReadOnly()) {
        	RoleResource roleResource = roleResourceRepository.findById(1, 1);
        	assertNull(roleResource);
        }

	}

	public static class RoleResourceRepository extends AbstractRoleResourceRepository {

		public RoleResourceRepository(Database database) {
			super(database);
		}


	}

}
