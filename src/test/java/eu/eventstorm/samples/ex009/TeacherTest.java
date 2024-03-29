package eu.eventstorm.samples.ex009;

import java.sql.Connection;
import java.sql.Statement;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eu.eventstorm.sql.Database;
import eu.eventstorm.sql.Dialect;
import eu.eventstorm.sql.Transaction;
import eu.eventstorm.sql.impl.DatabaseBuilder;
import eu.eventstorm.sql.impl.TransactionManagerImpl;

class TeacherTest {

    private JdbcConnectionPool ds;
    private Database database;
    private TeacherRepository repository;

    @BeforeEach
    void before() {
        ds = JdbcConnectionPool.create("jdbc:h2:mem:test;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1", "sa", "");
        database = DatabaseBuilder.from(Dialect.Name.H2)
        		.withTransactionManager(new TransactionManagerImpl(ds))
        		.withModule(new Module("ex009", ""))
        		.build();
        repository = new TeacherRepository(database);
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
    void test001() {
        try (Transaction tx = database.transactionManager().newTransactionReadOnly()) {
            ImmutableTeacher teacher = repository.findById(1);
            Assertions.assertNotNull(teacher);
            Assertions.assertEquals("CODE__01", teacher.getCode());
        }
    }


    private static class TeacherRepository extends AbstractImmutableTeacherRepository {

        protected TeacherRepository(Database database) {
            super(database);
        }
 
    }
   
}
