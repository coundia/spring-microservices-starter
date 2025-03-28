package com.pcoundia.infrastructure.controller;

import com.pcoundia.products.application.dto.ProductRequestDTO;
import com.pcoundia.products.infrastructure.entity.Product;
import com.pcoundia.shared.BaseIntegrationTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateProductNameProductControllerTests extends BaseIntegrationTests {

    @Test
    void it_should_be_able_to_update_name_of_product() {
        String id = UUID.randomUUID().toString();
        String productName = "test product";
        double productPrice = 30.0;

         productRepository.insert(id, productName, productPrice).block();

        ProductRequestDTO requestBody = new ProductRequestDTO("New name",0);

        String url = "/v1/commands/products/" + id;
        this.put(url,requestBody).expectStatus().isOk();
    }
}
