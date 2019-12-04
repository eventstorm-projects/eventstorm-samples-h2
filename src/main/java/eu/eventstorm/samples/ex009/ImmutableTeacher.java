package eu.eventstorm.samples.ex009;

import eu.eventstorm.sql.annotation.BusinessKey;
import eu.eventstorm.sql.annotation.Column;
import eu.eventstorm.sql.annotation.Flyway;
import eu.eventstorm.sql.annotation.PrimaryKey;
import eu.eventstorm.sql.annotation.Table;

@Table( value = "immutable_teacher", flyway = @Flyway(version = "1.0.0.009" , description = "init_schema"), immutable = true)
public interface ImmutableTeacher {

    @PrimaryKey(value = "id")
    long getId();

    void setId(long id);
    
    @Column("code")
    @BusinessKey
    String getCode();

    void setCode(String code);

}