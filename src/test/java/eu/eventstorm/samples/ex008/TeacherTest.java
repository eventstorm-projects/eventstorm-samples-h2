package eu.eventstorm.samples.ex008;

import static eu.eventstorm.samples.ex008.Factory.newTeacher;

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
import eu.eventstorm.sql.impl.DatabaseImpl;
import eu.eventstorm.sql.impl.Transaction;
import eu.eventstorm.sql.impl.TransactionManagerImpl;

class TeacherTest {

    private JdbcConnectionPool ds;
    private Database database;
    private TeacherRepository repository;

    @BeforeEach
    void before() {
        ds = JdbcConnectionPool.create("jdbc:h2:mem:test;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1", "sa", "");
        database = new DatabaseImpl(Dialect.Name.H2, new TransactionManagerImpl(ds), "", new Module("ex008", ""));
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


        try (Transaction tx = database.transactionManager().newTransactionReadWrite()) {
            Teacher teacher = newTeacher();
            teacher.setId(1);
            teacher.setKey(100);
            teacher.setCode("CODE__01");
            
            Teacher t2 = newTeacher();
            t2.setId(2);
            t2.setKey(100);
            t2.setCode("CODE__02");
            
            repository.insert(teacher);
            repository.insert(t2);
            tx.commit();
        }

        
        try (Transaction tx = database.transactionManager().newTransactionReadOnly()) {
            Teacher teacher = repository.findById(1,100);
            Assertions.assertNotNull(teacher);
            Assertions.assertEquals("CODE__01", teacher.getCode());
        }
    }


    private static class TeacherRepository extends AbstractTeacherRepository {

        protected TeacherRepository(Database database) {
            super(database);
        }
 
    }
   
}
