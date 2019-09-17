package eu.eventstorm.samples.ex001;

import eu.eventstorm.sql.annotation.Database;
import eu.eventstorm.sql.annotation.FlywayConfiguration;
import eu.eventstorm.sql.annotation.GlobalConfiguration;

@GlobalConfiguration(flywayConfiguration = @FlywayConfiguration(database = {Database.H2 }, packages = "eu.eventstorm.samples.ex001"))
public interface Ex001GlobalConfiguration {

}