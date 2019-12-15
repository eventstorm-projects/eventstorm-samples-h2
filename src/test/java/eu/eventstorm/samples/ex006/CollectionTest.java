package eu.eventstorm.samples.ex006;

import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Stream;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eu.eventstorm.sql.Database;
import eu.eventstorm.sql.Dialect;
import eu.eventstorm.sql.Transaction;
import eu.eventstorm.sql.expression.Expressions;
import eu.eventstorm.sql.impl.DatabaseBuilder;
import eu.eventstorm.sql.impl.TransactionManagerImpl;

public class CollectionTest {

	private JdbcConnectionPool ds;
	private Database database;

	@BeforeEach
	void before() throws Exception {
		ds = JdbcConnectionPool.create("jdbc:h2:mem:test;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1", "sa", "");
		database = DatabaseBuilder.from(Dialect.Name.H2)
        		.withTransactionManager(new TransactionManagerImpl(ds))
        		.withModule(new Module("ex006", ""))
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

		Resource res1 = Factory.newResource();
		res1.setId(1);
		res1.setName("/api/1.0/{id}");
		res1.setRoleId(r1.getId());
		
		Resource res2 = Factory.newResource();
		res2.setId(2);
		res2.setName("/api/2.0/{id}");
		res2.setRoleId(r1.getId());

		AbstractRoleRepository repository = new AbstractRoleRepository(database) {
			
        };

        ResourceRepository resourceRepository = new ResourceRepository(database);
        
        try (Transaction tx = database.transactionManager().newTransactionReadWrite()) {
        	repository.insert(r1);
        	resourceRepository.insert(res1);
        	resourceRepository.insert(res2);
        	tx.commit();
        }

        try (Transaction tx = database.transactionManager().newTransactionReadOnly()) {
        	Stream<Resource> resources = resourceRepository.findByRole(r1.getId());
        	resources.forEach(System.out::println);
        	tx.rollback();
        }
        
	}
	
	private static final class ResourceRepository extends AbstractResourceRepository {

		protected ResourceRepository(Database database) {
			super(database);
		}
		
		Stream<Resource> findByRole(int roleId) {
    		String select = select(ResourceDescriptor.ALL).from(ResourceDescriptor.TABLE).where(Expressions.eq(ResourceDescriptor.ROLE_ID)).build();
    		return stream(select, ps -> ps.setInt(1, roleId), Mappers.RESOURCE);
    	}
		
	}
}
