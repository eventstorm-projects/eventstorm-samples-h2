package eu.eventstorm.samples.ex001;

import java.sql.Timestamp;

import eu.eventstorm.sql.annotation.BusinessKey;
import eu.eventstorm.sql.annotation.Column;
import eu.eventstorm.sql.annotation.CreateTimestamp;
import eu.eventstorm.sql.annotation.Flyway;
import eu.eventstorm.sql.annotation.PrimaryKey;
import eu.eventstorm.sql.annotation.Table;

@Table( value = "student", flyway = @Flyway(version = "1.0.0.001" , description = "init_schema"))
public interface Student {

    @PrimaryKey("id")
    int getId();

    void setId(int id);

    @Column("code")
    @BusinessKey
    String getCode();

    void setCode(String code);

    @Column("age")
    Integer getAge();

    void setAge(Integer integer);

    @Column(value= "overall_rating" , nullable = true)
    Long getOverallRating();

    void setOverallRating(Long value);

    @Column(value= "created_at" , updatable = false)
    @CreateTimestamp
    Timestamp getCreatedAt();

    void setCreatedAt(Timestamp value);

    @Column(value= "readonly" , nullable = true, insertable = false, updatable = false)
    String getReadonly();

    void setReadonly(String value);

}
