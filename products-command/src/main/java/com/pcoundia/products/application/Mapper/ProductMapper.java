package com.pcoundia.products.application.Mapper;

import com.pcoundia.products.application.command.CreateProductCommand;
import com.pcoundia.products.application.dto.ProductRequestDTO;
import com.pcoundia.products.domain.aggregate.ProductAggregate;

public class ProductMapper {

    public static ProductRequestDTO toRequestDto(ProductAggregate productModel) {
        return new ProductRequestDTO(
                productModel.getName().value(),
                productModel.getPrice().value()
        );
    }

    public static CreateProductCommand toCommand(ProductRequestDTO productRequestDTO) {
        return new CreateProductCommand(
                productRequestDTO.getName(),
                productRequestDTO.getPrice()
        );
    }
}
