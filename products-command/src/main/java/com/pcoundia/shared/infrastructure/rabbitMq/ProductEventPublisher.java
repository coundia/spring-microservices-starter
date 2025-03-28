package com.pcoundia.shared.infrastructure.rabbitMq;


import com.pcoundia.products.application.event.ProductCreatedEvent;
import com.pcoundia.products.application.projections.ProductProjection;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ProductEventPublisher {

    private final RabbitTemplate rabbitTemplate;


    public ProductEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) {

        Logger.getLogger(ProductProjection.class.getName()).info("Publishing event: " + productCreatedEvent);

        rabbitTemplate.convertAndSend("axon-exchange", "product.created", productCreatedEvent);
    }
}
