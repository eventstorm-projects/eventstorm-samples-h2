package eu.eventstorm.samples.event.ex001;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import eu.eventstorm.samples.event.ex001.command.CommandFactory;
import eu.eventstorm.samples.event.ex001.command.CreateUserCommand;

@SpringBootTest(classes = Ex001Configuration.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class Ex001EventTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int localport;
	
	@Test
	void testCommand() {
	
		CreateUserCommand command = CommandFactory.newCreateUserCommand();
		command.setName("jacques");
		command.setAge(39);
		command.setEmail("jm@test.org");
		
		ResponseEntity<String> value = restTemplate.postForEntity("http://localhost:" + localport + "/command/user/create" , command, String.class);
		
		System.out.println(value);	
		
	}
}
