package eu.eventstorm.samples.event.ex001.event;

import eu.eventstorm.core.EventPayload;
import eu.eventstorm.core.annotation.CqrsEventPayload;
import eu.eventstorm.samples.event.ex001.domain.UserDomain;

/**
 * @author <a href="mailto:jacques.militello@gmail.com">Jacques Militello</a>
 */
@CqrsEventPayload(domain = UserDomain.class)
public interface UserCreatedEventPayload extends EventPayload {

	String getName();

	int getAge();

	String getEmail();

}
