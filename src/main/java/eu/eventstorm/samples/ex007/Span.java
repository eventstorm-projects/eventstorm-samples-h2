package eu.eventstorm.samples.ex007;

import eu.eventstorm.sql.annotation.BusinessKey;
import eu.eventstorm.sql.annotation.Column;
import eu.eventstorm.sql.annotation.Flyway;
import eu.eventstorm.sql.annotation.PrimaryKey;
import eu.eventstorm.sql.annotation.Table;
import eu.eventstorm.sql.type.Json;

@Table(value = "span" , flyway = @Flyway(version = "1.0.0.007" , description = "init_schema"))
public interface Span {

	@PrimaryKey("id")
	int getId();

	void setId(int id);

	@Column(value = "name", length = 64)
	@BusinessKey
	String getName();

	void setName(String name);
	
	@Column("content")
	Json getContent();
	
	void setContent(Json json);
	
}
