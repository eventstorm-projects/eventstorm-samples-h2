package eu.eventstorm.samples.ex004;

import eu.eventstorm.sql.annotation.Database;
import eu.eventstorm.sql.annotation.FlywayConfiguration;
import eu.eventstorm.sql.annotation.GlobalConfiguration;

@GlobalConfiguration(flywayConfiguration = @FlywayConfiguration(database = {Database.H2 }, packages = "eu.eventstorm.samples.ex004"))
public interface Ex004GlobalConfiguration {

}