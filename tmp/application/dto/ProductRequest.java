package .Users.pcoundia.projects.spring-microservices-starter.tmp.application.dto;

import .Users.pcoundia.projects.spring-microservices-starter.tmp.domain.valueObject.*;

public record ProductRequest(
    ProductId id,
    ProductName name,
    ProductPrice price
) {}
