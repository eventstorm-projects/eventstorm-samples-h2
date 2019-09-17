package eu.eventstorm.samples.ex001;

import eu.eventstorm.sql.annotation.AutoIncrement;
import eu.eventstorm.sql.annotation.Column;
import eu.eventstorm.sql.annotation.Flyway;
import eu.eventstorm.sql.annotation.PrimaryKey;
import eu.eventstorm.sql.annotation.Table;

@Table( value = "auto_increment_pojo", flyway = @Flyway(version = "1.0.0.001" , description = "init_schema"))
public interface AutoIncrementPojo {

    @PrimaryKey("id")
    @AutoIncrement
    int getId();

    void setId(int id);

    @Column("name")
    String getName();

    void setName(String name);
}
