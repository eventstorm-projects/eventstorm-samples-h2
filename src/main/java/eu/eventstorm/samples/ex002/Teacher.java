package eu.eventstorm.samples.ex002;

import java.sql.Timestamp;

import eu.eventstorm.sql.annotation.BusinessKey;
import eu.eventstorm.sql.annotation.Column;
import eu.eventstorm.sql.annotation.CreateTimestamp;
import eu.eventstorm.sql.annotation.Flyway;
import eu.eventstorm.sql.annotation.PrimaryKey;
import eu.eventstorm.sql.annotation.Sequence;
import eu.eventstorm.sql.annotation.Table;
import eu.eventstorm.sql.annotation.UpdateTimestamp;

@Table( value = "teacher", flyway = @Flyway(version = "1.0.0.002" , description = "init_schema"))
public interface Teacher {

    @PrimaryKey(value = "id")
    @Sequence("seq_teacher")
    long getId();

    void setId(long id);

    @Column("code")
    @BusinessKey
    String getCode();

    void setCode(String code);

    @Column("prefix_code")
    @BusinessKey
    String getPrefixCode();

    void setPrefixCode(String prefixCode);

    @Column("create_ts")
    @CreateTimestamp
    Timestamp getCreationTimestamp();

    void setCreationTimestamp(Timestamp timestamp);

    @Column(value = "update_ts", nullable = true)
    @UpdateTimestamp
    Timestamp getUpdateTimestamp();

    void setUpdateTimestamp(Timestamp timestamp);
}