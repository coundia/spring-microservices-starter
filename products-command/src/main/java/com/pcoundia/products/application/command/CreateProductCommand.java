package com.pcoundia.products.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class CreateProductCommand implements Serializable {
    @TargetAggregateIdentifier
    private String name;
    private Double price;

}
