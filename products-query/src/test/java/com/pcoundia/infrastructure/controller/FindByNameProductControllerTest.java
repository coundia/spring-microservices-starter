package com.pcoundia.infrastructure.controller;

import com.pcoundia.products.application.dto.ListProductResponseDTO;
import com.pcoundia.products.application.dto.ProductResponseDTO;
import com.pcoundia.products.infrastructure.entity.Product;
import com.pcoundia.shared.BaseIntegrationTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class FindByNameProductControllerTest extends BaseIntegrationTests {

    @Test
    void it_should_find_products_by_name() {
        productRepository.insert(UUID.randomUUID().toString(), "Product1", 100.0).block();
        productRepository.insert(UUID.randomUUID().toString(), "Product2", 200.0).block();
        productRepository.insert(UUID.randomUUID().toString(), "No name", 300.0).block();

        this.get("/v1/queries/products/name?name=product")
            .expectStatus()
            .isOk()
            .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
            .expectBody(ListProductResponseDTO.class)
            .value(response -> {
                assertThat(response).isNotNull();
                assertThat(response.getProducts().size()).isEqualTo(2);
            });

    }
}
