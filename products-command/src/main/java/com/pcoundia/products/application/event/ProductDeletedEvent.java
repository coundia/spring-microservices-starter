package com.pcoundia.products.application.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class ProductDeletedEvent implements Serializable {
    private String productId;
}
