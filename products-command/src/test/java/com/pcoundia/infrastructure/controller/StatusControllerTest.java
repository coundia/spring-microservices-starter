package com.pcoundia.infrastructure.controller;

import com.pcoundia.shared.BaseIntegrationTests;
import org.junit.jupiter.api.Test;

public class StatusControllerTest extends BaseIntegrationTests {

    @Test
    void it_should_get_status_gateway() {
        this
            .get("/v1/status")
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.code").isEqualTo(1)
            .jsonPath("$.message").isEqualTo("I am Gateway.")
        ;
    }


}
