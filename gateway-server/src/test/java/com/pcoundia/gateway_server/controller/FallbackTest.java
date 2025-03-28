package com.pcoundia.gateway_server.controller;

import com.pcoundia.gateway_server.shared.BaseIntegrationTests;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@AutoConfigureWireMock
public class FallbackTest extends BaseIntegrationTests {

    @Test
    public void contextLoads() throws Exception {

        //Simule l' API GET /api/v1/status
        stubFor(get(urlEqualTo("/api/v1/status"))
            .willReturn(aResponse()
                .withBody("{ \"code\": 1, \"message\": \"I am Gateway.\"}")
                .withHeader("Content-Type", "application/json")));

        // Simule l' API GET /delay/3
        // qui prend 3 secondes avant de répondre
        // "no fallback", ce qui déclenchera un timeout et le Circuit Breaker.
        stubFor(get(urlEqualTo("/delay/3"))
            .willReturn(aResponse()
                .withBody("no fallback")
                .withFixedDelay(3000)));

        //L'API GET /ap/v1/status retourne bien la réponse attendue.
        webTestClient
            .get().uri("/api/v1/status")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.code").isEqualTo(1)
            .jsonPath("$.message").isEqualTo("I am Gateway.")
        ;

        //L'API GET /delay/3 déclenche un timeout et active un fallback grâce à un Circuit Breaker.
        webTestClient
            .get().uri("/delay/3")
            .header("Host", "www.circuitbreaker.com")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.code").isEqualTo(1)
            .jsonPath("$.message").isEqualTo("I am Gateway fallback.");
    }
}
