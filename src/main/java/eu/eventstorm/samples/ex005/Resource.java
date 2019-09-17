package eu.eventstorm.samples.ex005;

import eu.eventstorm.sql.annotation.BusinessKey;
import eu.eventstorm.sql.annotation.Column;
import eu.eventstorm.sql.annotation.Flyway;
import eu.eventstorm.sql.annotation.PrimaryKey;
import eu.eventstorm.sql.annotation.Table;

@Table(value = "resource", flyway = @Flyway(version = "1.0.0.005", description = "init_schema"))
public interface Resource {

	@PrimaryKey("id")
	int getId();

	void setId(int id);

	@Column(value = "name", length = 64)
	@BusinessKey
	String getName();

	void setName(String name);

}
