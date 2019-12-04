package eu.eventstorm.samples.ex007;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.Statement;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eu.eventstorm.sql.Database;
import eu.eventstorm.sql.Dialect;
import eu.eventstorm.sql.impl.DatabaseImpl;
import eu.eventstorm.sql.impl.Transaction;
import eu.eventstorm.sql.impl.TransactionManagerImpl;

public class JsonTest {

	private JdbcConnectionPool ds;
	private Database database;

	@BeforeEach
	void before() throws Exception {
		ds = JdbcConnectionPool.create("jdbc:h2:mem:test;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1", "sa", "");
		database = new DatabaseImpl(Dialect.Name.H2, new TransactionManagerImpl(ds), "", new Module("ex007", ""));
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

		Span span = Factory.newSpan();
		span.setId(1);
		span.setName("span001");
		span.setContent(database.dialect().createJson("{\"key\" : \"value\"}".getBytes()));

		AbstractSpanRepository repository = new AbstractSpanRepository(database) {
			
        };

        
        try (Transaction tx = database.transactionManager().newTransactionReadWrite()) {
        	repository.insert(span);
        	tx.commit();
        }

        try (Transaction tx = database.transactionManager().newTransactionReadOnly()) {
        	Span s = repository.findById(1);
        	assertEquals("value", s.getContent().asMap().get("key", String.class));
        	tx.rollback();
        }
        
        try (Transaction tx = database.transactionManager().newTransactionReadWrite()) {
        	Span s = repository.findByIdForUpdate(1);
        	s.getContent().asMap().put("key" , "value2");
        	repository.update(s);
        	tx.commit();
        }
        
        try (Transaction tx = database.transactionManager().newTransactionReadOnly()) {
        	Span s = repository.findById(1);
        	assertEquals("value2", s.getContent().asMap().get("key", String.class));
        	tx.rollback();
        }
	}
	
}
