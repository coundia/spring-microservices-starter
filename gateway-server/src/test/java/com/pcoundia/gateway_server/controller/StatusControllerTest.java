package com.pcoundia.gateway_server.controller;

import com.pcoundia.gateway_server.shared.BaseIntegrationTests;
import org.junit.jupiter.api.Test;

public class StatusControllerTest extends BaseIntegrationTests {

    @Test
    void it_should_get_status_gateway() {
        webTestClient
            .get()
            .uri(this.getBaseUrl() + "/v1/status")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.code").isEqualTo(1)
            .jsonPath("$.message").isEqualTo("I am Gateway.")
        ;
    }


}
