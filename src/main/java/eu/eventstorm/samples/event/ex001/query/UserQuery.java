package eu.eventstorm.samples.event.ex001.query;

import eu.eventstorm.core.annotation.CqrsQueryDatabaseProperty;
import eu.eventstorm.core.annotation.CqrsQueryDatabaseView;
import eu.eventstorm.sql.annotation.View;
import eu.eventstorm.sql.annotation.ViewColumn;

@CqrsQueryDatabaseView(view = @View("user_query"))
public interface UserQuery {

	@CqrsQueryDatabaseProperty(column = @ViewColumn("user_name"))
	String getUserName();
	
}
