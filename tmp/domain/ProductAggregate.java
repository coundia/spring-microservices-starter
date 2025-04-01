package .Users.pcoundia.projects.spring-microservices-starter.tmp.domain;

import .Users.pcoundia.projects.spring-microservices-starter.tmp.domain.valueObject.*;
import .Users.pcoundia.projects.spring-microservices-starter.tmp.domain.event.*;
import .Users.pcoundia.projects.spring-microservices-starter.tmp.application.command.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class ProductAggregate {

@AggregateIdentifier
private ProductId id;

private ProductName name;
private ProductPrice price;

protected ProductAggregate() {}

@CommandHandler
public ProductAggregate(CreateProductCommand command) {
apply(new ProductCreatedEvent(
            command.id(),
            command.name(),
            command.price()
));
}

@EventSourcingHandler
public void on(ProductCreatedEvent event) {
this.id = event.id();
        this.name = event.name();
        this.price = event.price();
}
}
