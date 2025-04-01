package .Users.pcoundia.projects.spring-microservices-starter.tmp.application.dto;

import .Users.pcoundia.projects.spring-microservices-starter.tmp.domain.valueObject.*;

public record ProductResponse(
    ProductId id,
    ProductName name,
    ProductPrice price
) {}
