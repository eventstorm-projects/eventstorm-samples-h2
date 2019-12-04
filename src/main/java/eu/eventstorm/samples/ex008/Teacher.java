package eu.eventstorm.samples.ex008;

import eu.eventstorm.sql.annotation.BusinessKey;
import eu.eventstorm.sql.annotation.Column;
import eu.eventstorm.sql.annotation.Flyway;
import eu.eventstorm.sql.annotation.PrimaryKey;
import eu.eventstorm.sql.annotation.Table;

@Table( value = "teacher2key", flyway = @Flyway(version = "1.0.0.008" , description = "init_schema"))
public interface Teacher {

    @PrimaryKey(value = "id")
    long getId();

    void setId(long id);

    @PrimaryKey(value = "key")
    long getKey();

    void setKey(long key);
    
    @Column("code")
    @BusinessKey
    String getCode();

    void setCode(String code);

}