package eu.eventstorm.samples.event.ex001.command;

import static eu.eventstorm.core.annotation.CqrsCommandType.CREATE;
import static eu.eventstorm.core.annotation.HttpMethod.POST;

import eu.eventstorm.core.Command;
import eu.eventstorm.core.annotation.CqrsCommand;
import eu.eventstorm.core.annotation.CqrsCommandRestController;

@CqrsCommand(type = CREATE)
@CqrsCommandRestController(name = "UserCommandRestController", javaPackage = "eu.eventstorm.samples.event.ex001.rest", method = POST, uri = "command/user/create")
public interface CreateUserCommand extends Command {

    String getName();

    void setName(String name);

    int getAge();

    void setAge(int age);

    String getEmail();

    void setEmail(String email);

}