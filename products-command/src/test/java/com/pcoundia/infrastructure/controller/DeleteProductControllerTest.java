package com.pcoundia.infrastructure.controller;

import com.pcoundia.products.infrastructure.entity.Product;
import com.pcoundia.shared.BaseIntegrationTests;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteProductControllerTest extends BaseIntegrationTests {

    @Test
    void it_should_delete_product_by_id_and_return_ok() {
        String id = UUID.randomUUID().toString();
        String productName = "test product to delete";
        double productPrice = 25.99;
        productRepository.insert(id, productName, productPrice).block();
        String deleteUrl = "/v1/commands/products/" + id;
        this.delete(deleteUrl).expectStatus().isOk();
    }

    @Test
    void it_should_return_not_found_if_product_id_does_not_exist() {
        String nonExistingId = UUID.randomUUID().toString();
        String deleteUrl = "/v1/commands/products/" + nonExistingId;
        this.delete(deleteUrl).expectStatus().isNotFound();
    }
}
