package eu.eventstorm.samples.event.ex001.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import eu.eventstorm.core.AggregateId;
import eu.eventstorm.core.Event;
import eu.eventstorm.core.EventStore;
import eu.eventstorm.core.id.AggregateIdGenerator;
import eu.eventstorm.core.id.AggregateIdGeneratorManager;
import eu.eventstorm.core.impl.AbstractCommandHandler;
import eu.eventstorm.samples.event.ex001.command.CreateUserCommand;
import eu.eventstorm.samples.event.ex001.event.EventPayloadFactory;
import eu.eventstorm.samples.event.ex001.event.UserCreatedEventPayload;

@Component
public final class CreateUserCommandHandler extends AbstractCommandHandler<CreateUserCommand>  {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateUserCommandHandler.class);

	private final AggregateIdGenerator aig;
	
	public CreateUserCommandHandler(EventStore eventStore, AggregateIdGeneratorManager aigm) {
		super(CreateUserCommand.class, eventStore);
		this.aig = aigm.getAggregateIdGenerator("user");
	}

	@Override
	public ImmutableList<Event> handle(CreateUserCommand command) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("handle ({})", command);
		}

		//1. validate on master data.
		
		AggregateId id = this.aig.generate();
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("generate ({})", id);
		}
		
		UserCreatedEventPayload eventData = EventPayloadFactory.newUserCreatedEventPayload(
				command.getName(),
				command.getAge(),
				command.getEmail()
				);

		Event event = getEventStore().appendToStream("user", id, eventData);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("event ({})", event);
		}
		
		return ImmutableList.of(event);
		
	}

}
