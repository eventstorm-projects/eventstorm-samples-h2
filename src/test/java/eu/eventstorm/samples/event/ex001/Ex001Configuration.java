package eu.eventstorm.samples.event.ex001;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.Module;
import com.google.common.collect.ImmutableMap;

import eu.eventstorm.core.CommandGateway;
import eu.eventstorm.core.CommandHandlerRegistry;
import eu.eventstorm.core.EventBus;
import eu.eventstorm.core.EventStore;
import eu.eventstorm.core.eventbus.InMemoryEventBus;
import eu.eventstorm.core.eventstore.InMemoryEventStore;
import eu.eventstorm.core.id.AggregateIdGenerator;
import eu.eventstorm.core.id.AggregateIdGeneratorFactory;
import eu.eventstorm.core.id.AggregateIdGeneratorManager;
import eu.eventstorm.core.json.jackson.CloudEventsModule;
import eu.eventstorm.samples.event.ex001.command.json.CommandModule;
import eu.eventstorm.samples.event.ex001.handler.CreateUserCommandHandler;

@ComponentScan({"eu.eventstorm.samples.event.ex001.rest" , "eu.eventstorm.samples.event.ex001.handler"})
@Configuration
@EnableAutoConfiguration
public class Ex001Configuration {

	@Bean
	CommandHandlerRegistry registry(CreateUserCommandHandler handler) {
		CommandHandlerRegistry.Builder builder = CommandHandlerRegistry.newBuilder();
		//chs.forEach(ch -> builder.add(ch.getType(), ch));
		builder.add(handler);
		return builder.build();
	}
	
	@Bean
	EventStore eventStore() {
		return new InMemoryEventStore();
	}
	
	@Bean
	EventBus eventBus() {
		return InMemoryEventBus.builder().build();
	}
	
	@Bean
	AggregateIdGeneratorManager aggregateIdGeneratorManager() {
		ImmutableMap.Builder<String, AggregateIdGenerator> builder = ImmutableMap.builder();
		builder.put("user", AggregateIdGeneratorFactory.inMemoryInteger());
		return new AggregateIdGeneratorManager(builder.build());
	}
	
	@Bean
	CommandGateway commandGateway(CommandHandlerRegistry registry, EventBus eventBus) {
		CommandGateway gateway = new CommandGateway(registry, eventBus);
		
		return gateway;
	}
	
	@Bean
	Module module() {
		return new CommandModule();
	}
	
	@Bean
	Module cloudEventsModule() {
		return new CloudEventsModule();
	}
}
