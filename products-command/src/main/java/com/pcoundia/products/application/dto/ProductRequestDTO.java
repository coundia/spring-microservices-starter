package com.pcoundia.products.application.dto;

import lombok.Getter;

@Getter
public class ProductRequestDTO extends ProductDTO {

    public ProductRequestDTO(String name,double price ) {
        this.setName(name);
        this.setPrice(price);
    }

    public static ProductRequestDTO create(double price, String name) {
        return new ProductRequestDTO(name,price);
    }

}
