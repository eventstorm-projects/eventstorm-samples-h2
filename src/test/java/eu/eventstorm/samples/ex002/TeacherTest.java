package eu.eventstorm.samples.ex002;

import static eu.eventstorm.samples.ex002.Factory.newTeacher;
import static eu.eventstorm.samples.ex002.TeacherDescriptor.ID;
import static eu.eventstorm.samples.ex002.TeacherDescriptor.TABLE;
import static eu.eventstorm.sql.expression.AggregateFunctions.count;

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
import eu.eventstorm.sql.jdbc.PreparedStatementSetter;
import eu.eventstorm.sql.jdbc.ResultSetMappers;

class TeacherTest {

    private JdbcConnectionPool ds;
    private Database database;
    private TeacherRepository repository;

    @BeforeEach
    void before() {
        ds = JdbcConnectionPool.create("jdbc:h2:mem:test;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1", "sa", "");
        database = DatabaseBuilder.from(Dialect.Name.H2)
        		.withTransactionManager(new TransactionManagerImpl(ds))
        		.withModule(new Module("ex002", ""))
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
            Teacher teacher = repository.findById(1);
            Assertions.assertNull(teacher);
        }

        try (Transaction tx = database.transactionManager().newTransactionReadWrite()) {
            Teacher teacher = newTeacher();
            teacher.setCode("0032");
            teacher.setPrefixCode("ABCD");
            repository.insert(teacher);
            tx.commit();
        }

        try (Transaction tx = database.transactionManager().newTransactionReadOnly()) {
            Teacher teacher = repository.findById(1);
            Assertions.assertNotNull(teacher);
            Assertions.assertEquals("0032", teacher.getCode());
            Assertions.assertEquals("ABCD", teacher.getPrefixCode());
            System.out.println(teacher);
        }

        try (Transaction tx = database.transactionManager().newTransactionReadWrite()) {
            for (int i = 0; i < 8192; i++) {
                Teacher teacher = newTeacher();
                teacher.setCode("0032" + i);
                teacher.setPrefixCode("ABCD" + i);
                repository.insert(teacher);
            }
            tx.commit();
        }

        try (Transaction tx = database.transactionManager().newTransactionReadOnly()) {
            Assertions.assertEquals(8193, repository.countAll());
        }
    }

    @Test
    void test002() {

        // create
        try (Transaction tx = database.transactionManager().newTransactionReadWrite()) {
            Teacher teacher = newTeacher();
            teacher.setCode("0032");
            teacher.setPrefixCode("ABCD");
            repository.insert(teacher);
            tx.commit();
        }

        try (Transaction tx = database.transactionManager().newTransactionReadOnly()) {
            Teacher teacher = repository.findById(1);
            Assertions.assertNotNull(teacher);
            Assertions.assertNotNull(teacher.getCreationTimestamp());
            Assertions.assertNull(teacher.getUpdateTimestamp());
        }

        try (Transaction tx = database.transactionManager().newTransactionReadWrite()) {
            Teacher teacher = repository.findById(1);
            teacher.setCode("DEFG");
            repository.update(teacher);
            tx.commit();
        }

        try (Transaction tx = database.transactionManager().newTransactionReadOnly()) {
            Teacher teacher = repository.findByIdForUpdate(1);
            Assertions.assertNotNull(teacher);
            Assertions.assertNotNull(teacher.getCreationTimestamp());
            Assertions.assertNotNull(teacher.getUpdateTimestamp());
        }

    }

    private static class TeacherRepository extends AbstractTeacherRepository {

        private final String countAll;

        protected TeacherRepository(Database database) {
            super(database);
            countAll = select(count(ID)).from(TABLE).build();
        }

        long countAll() {
            return executeSelect(this.countAll, PreparedStatementSetter.EMPTY, ResultSetMappers.SINGLE_LONG);
        }


    }
}
