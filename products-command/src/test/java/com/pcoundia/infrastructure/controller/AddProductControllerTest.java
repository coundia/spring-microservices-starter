package com.pcoundia.infrastructure.controller;

import com.pcoundia.products.application.Mapper.ProductMapper;
import com.pcoundia.products.application.dto.ProductRequestDTO;
import com.pcoundia.products.application.dto.ProductResponseDTO;
import com.pcoundia.products.domain.aggregate.ProductAggregate;
import com.pcoundia.products.domain.valueObject.ProductName;
import com.pcoundia.products.domain.valueObject.ProductPrice;
import com.pcoundia.shared.BaseIntegrationTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

public class AddProductControllerTest extends BaseIntegrationTests {

    @Test
    void it_should_be_able_to_add_product() {
        ProductAggregate productModel = new ProductAggregate(
            ProductName.create("Product 1"),
            ProductPrice.create(1000)
        );

        ProductRequestDTO productRequestDTO = ProductMapper.toRequestDto(productModel);

        String endpoint = "/v1/commands/products";

        this.post(endpoint, productRequestDTO)
            .expectStatus().isOk()
            .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
            .expectBody(ProductResponseDTO.class)
            .value(response -> {
                assertThat(response).isNotNull();
                assertThat(response.getId()).isNotEmpty();
                assertThat(response.getName()).isEqualTo(productRequestDTO.getName());
                assertThat(response.getPrice()).isEqualTo(productRequestDTO.getPrice());
            });
    }
}
