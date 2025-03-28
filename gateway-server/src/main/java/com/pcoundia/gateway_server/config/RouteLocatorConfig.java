package com.pcoundia.gateway_server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteLocatorConfig {

    @Value("${external.server:https://localhost}")
    private String externalServerUri  ;

    @Bean
    public RouteLocator myRoutes(
        RouteLocatorBuilder builder
    ) {

        return
            builder
                .routes()

                .route(p -> p
                    .path("/api/v1/status")
                    .filters(
                        f
                            -> f.addRequestHeader(
                            "Hello", "World")
                    )
                    .uri(externalServerUri))

                .route(p -> p
                    .host("*.circuitbreaker.com")
                    .filters(f -> f
                        .circuitBreaker(config -> config
                            .setName("mycmd")
                            .setFallbackUri("forward:/api/v1/status/fallback")))
                    .uri(externalServerUri))
                .build();
    }

}
