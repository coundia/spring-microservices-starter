package .Users.pcoundia.projects.spring-microservices-starter.tmp.domain.event;

import .Users.pcoundia.projects.spring-microservices-starter.tmp.domain.valueObject.*;

public record ProductCreatedEvent(
    ProductId id,
    ProductName name,
    ProductPrice price
) {}
