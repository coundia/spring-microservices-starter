package com.pcoundia.products.application.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class ProductCreatedEvent implements Serializable {
    private String id;
    private Double price;
    private String name;

    @JsonCreator
    public ProductCreatedEvent(String id, Double price, String name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    public String toString() {
        return "ProductCreatedEvent{" +
                "id='" + id + '\'' +
                ", price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}
