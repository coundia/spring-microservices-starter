package com.pcoundia.products.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Setter
@Getter
abstract class ProductDTO implements Serializable {
    @Schema(description = "Name of the product", example = "Laptop")
    private String name;
    @Schema(description = "Price of the product", example = "999.99")
    private double price;

}
