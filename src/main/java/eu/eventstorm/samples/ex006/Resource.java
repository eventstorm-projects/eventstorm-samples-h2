package eu.eventstorm.samples.ex006;

import eu.eventstorm.sql.annotation.AssociationType;
import eu.eventstorm.sql.annotation.BusinessKey;
import eu.eventstorm.sql.annotation.Column;
import eu.eventstorm.sql.annotation.Flyway;
import eu.eventstorm.sql.annotation.ForeignKey;
import eu.eventstorm.sql.annotation.PrimaryKey;
import eu.eventstorm.sql.annotation.Table;

@Table(value = "resource_t2", flyway = @Flyway(version = "1.0.0.006", description = "init_schema"))
public interface Resource {

	@PrimaryKey("id")
	int getId();

	void setId(int id);

	@Column(value = "name", length = 64)
	@BusinessKey
	String getName();

	void setName(String name);
	
	@Column(value = "role_id", length = 64)
	@ForeignKey(target = Role.class, type = AssociationType.MANY_TO_ONE)
	int getRoleId();
	
	void setRoleId(int roleId);

}
