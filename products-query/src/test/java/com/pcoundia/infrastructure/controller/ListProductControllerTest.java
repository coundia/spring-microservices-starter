package com.pcoundia.infrastructure.controller;

import com.pcoundia.products.application.dto.ListProductResponseDTO;
import com.pcoundia.products.infrastructure.entity.Product;
import com.pcoundia.shared.BaseIntegrationTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ListProductControllerTest extends BaseIntegrationTests {


    @Test
    void it_should_list_products() {
        productRepository.insert(UUID.randomUUID().toString(), "Product1", 100.0).block();
        productRepository.insert(UUID.randomUUID().toString(), "Product2", 200.0).block();

        this.get("/v1/queries/products")
            .expectStatus()
            .isOk()
            .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
            .expectBody(ListProductResponseDTO.class)
            .value(response -> {
                assertThat(response).isNotNull();
                assertThat(response.getProducts().size()).isEqualTo(2);
                assertThat(response.getCurrentPage()).isEqualTo(0);
                assertThat(response.getTotalElements()).isEqualTo(2);
                assertThat(response.getTotalElements()).isEqualTo(2);
            });



    }
}
