package eu.eventstorm.samples.event.ex001.query;

import eu.eventstorm.core.annotation.CqrsQueryDatabaseView;
import eu.eventstorm.sql.annotation.View;

@CqrsQueryDatabaseView(view = @View("user_query"))
public interface UserQuery {

	String getUserName();
	
}
