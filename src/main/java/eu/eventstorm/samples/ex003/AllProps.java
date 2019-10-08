package eu.eventstorm.samples.ex003;


import eu.eventstorm.sql.annotation.Column;
import eu.eventstorm.sql.annotation.PrimaryKey;
import eu.eventstorm.sql.annotation.Table;

@Table(value = "all_prop")
public interface AllProps {


    @PrimaryKey("id")
    int getId();

    void setId(int id);

    @Column("string")
    String getString();

    void setString(String code);

    @Column("byte")
    byte getByte_();

    void setByte_(byte b);

    @Column("o_byte")
    Byte getObjectByte();

    void setObjectByte(Byte b);

    @Column("short")
    short getShort_();

    void setShort_(short s);

    @Column("o_short")
    Short getObjectShort();

    void setObjectShort(Short s);

    @Column("int")
    int getInt_();

    void setInt_(int i);

    @Column("o_int")
    Integer getObjectInt();

    void setObjectInt(Integer i);

    @Column("long")
    long getLong_();

    void setLong_(long l);

    @Column("o_long")
    Long getObjectLong();

    void setObjectLong(Long l);

    @Column("float")
    float getFloat_();

    void setFloat_(float l);

    @Column("o_float")
    float getObjectFloat();

    void setObjectFloat(float l);

    @Column("double")
    double getDouble_();

    void setDouble_(double l);

    @Column("o_double")
    Double getObjectDouble();

    void setObjectDouble(Double l);

    @Column("boolean")
    boolean getBoolean_();

    void setBoolean_(boolean l);

    @Column("o_boolean")
    Boolean getObjectBoolean();

    void setObjectBoolean(Boolean l);

}
