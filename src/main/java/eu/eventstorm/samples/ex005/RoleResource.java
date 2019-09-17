package eu.eventstorm.samples.ex005;

import eu.eventstorm.sql.annotation.Flyway;
import eu.eventstorm.sql.annotation.JoinColumn;
import eu.eventstorm.sql.annotation.JoinTable;

@JoinTable(value = "role_resource", flyway = @Flyway(version = "1.0.0.005", description = "init_schema"))
public interface RoleResource {

	@JoinColumn(value = "role_id", target = Role.class)
	int getRoleId();

	void setRoleId(int roleId);

	@JoinColumn(value = "resource_id", target = Resource.class)
	int getResourceId();

	void setResourceId(int resourceId);

}
