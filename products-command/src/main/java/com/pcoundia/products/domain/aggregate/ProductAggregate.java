package com.pcoundia.products.domain.aggregate;

import com.pcoundia.products.application.command.CreateProductCommand;
import com.pcoundia.products.application.command.DeleteProductCommand;
import com.pcoundia.products.application.command.UpdateNameProductCommand;
import com.pcoundia.products.application.event.ProductCreatedEvent;
import com.pcoundia.products.application.event.ProductDeletedEvent;
import com.pcoundia.products.application.event.UpdateNameProductEvent;
import com.pcoundia.products.domain.valueObject.ProductId;
import com.pcoundia.products.domain.valueObject.ProductName;
import com.pcoundia.products.domain.valueObject.ProductPrice;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class ProductAggregate {
    @AggregateIdentifier
    private ProductId id;
    private ProductPrice price;
    private ProductName name;
    private Boolean isDeleted;

    protected ProductAggregate() {
    }

    public ProductAggregate(ProductName name, ProductPrice price) {
        this.id = ProductId.create(UUID.randomUUID().toString());
        this.price = price;
        this.name = name;
        this.isDeleted = false;
    }

    public ProductId getId() {
        return id;
    }

    public ProductPrice getPrice() {
        return price;
    }

    public ProductName getName() {
        return name;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        this.id = ProductId.create(event.getId());
        this.price = ProductPrice.create(event.getPrice());
        this.name = ProductName.create(event.getName());
        this.isDeleted = false;
    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand command) {
        this.id = ProductId.create(UUID.randomUUID().toString());
        apply(new ProductCreatedEvent(this.getId().value(), command.getPrice(), command.getName()));
    }

    @CommandHandler
    public ProductAggregate(DeleteProductCommand command) {
        this.id = ProductId.create(UUID.randomUUID().toString());
        apply(new ProductDeletedEvent(command.getProductId().value()));
    }

    @CommandHandler
    public ProductAggregate(UpdateNameProductCommand command) {
        this.id = ProductId.create(UUID.randomUUID().toString());
        apply(new UpdateNameProductEvent(command.getProductId().value(),command.getProductName().value()));
    }

}
