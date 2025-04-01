package .Users.pcoundia.projects.spring-microservices-starter.tmp.presentation.projection;

import .Users.pcoundia.projects.spring-microservices-starter.tmp.domain.event.*;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class ProductProjection {

@EventHandler
public void on(ProductCreatedEvent event) {
}

@EventHandler
public void on(ProductUpdatedEvent event) {
}

@EventHandler
public void on(ProductDeletedEvent event) {
}
}
