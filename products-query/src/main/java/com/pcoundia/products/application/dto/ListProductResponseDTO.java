package com.pcoundia.products.application.dto;

import com.pcoundia.products.infrastructure.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class ListProductResponseDTO implements Serializable {
    @Schema(description = "The list of products")
    private List<ProductResponseDTO> products;
    private long currentPage;
    private long limit;
    private long totalElements;

    public static ListProductResponseDTO from(List<Product> products, long page, long limit,long totalElements) {
        List<ProductResponseDTO> productsList = products.stream()
            .map(product -> new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getPrice()
            ))
            .collect(Collectors.toList());

        return new ListProductResponseDTO(productsList, page, limit, totalElements);
    }
}
