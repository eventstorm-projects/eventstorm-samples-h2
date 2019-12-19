package eu.eventstorm.samples.ex008;

import eu.eventstorm.sql.annotation.View;
import eu.eventstorm.sql.annotation.ViewColumn;

@View(value = "teacher_view")
public interface TeacherView {

	@ViewColumn("id")
	long getId();

	@ViewColumn("key")
	long getKey();

	@ViewColumn(value = "code", nullable = true)
	String getCode();

}