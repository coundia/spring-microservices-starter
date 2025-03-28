package com.pcoundia.products.application.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class UpdateNameProductEvent implements Serializable {
    private String productId;
    private String productName;

    @Override
    public String toString() {
        return "UpdateNameProductEvent{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }
}
