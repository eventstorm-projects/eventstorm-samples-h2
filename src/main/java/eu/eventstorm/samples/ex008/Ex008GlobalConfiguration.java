package eu.eventstorm.samples.ex008;

import eu.eventstorm.sql.annotation.Database;
import eu.eventstorm.sql.annotation.FlywayConfiguration;
import eu.eventstorm.sql.annotation.GlobalConfiguration;

@GlobalConfiguration(flywayConfiguration = @FlywayConfiguration(database = {Database.H2 }, packages = "eu.eventstorm.samples.ex008"))
public interface Ex008GlobalConfiguration {

}