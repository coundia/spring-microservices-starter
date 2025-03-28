package com.pcoundia.shared.infrastructure.rabbitMq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.fanoutExchange("axon-events").durable(true).build();
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable("events.queue").build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with("*").noargs();
    }
}

