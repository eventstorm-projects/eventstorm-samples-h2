package eu.eventstorm.samples.event.ex001.domain;

import eu.eventstorm.core.DomainModel;
import eu.eventstorm.core.annotation.CqrsDomain;

@CqrsDomain(name = "user")
public interface UserDomain extends DomainModel {

}
