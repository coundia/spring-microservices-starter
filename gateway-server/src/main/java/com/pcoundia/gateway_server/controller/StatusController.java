package com.pcoundia.gateway_server.controller;

import com.pcoundia.gateway_server.dto.ApiResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RefreshScope
@RestController
@RequestMapping("/api/v1")
public class StatusController {

    @Value("${message:Gateway}")
    private String message;

    @GetMapping("/status")
    public Mono<ApiResponseDto> getStatusGateway(){
        return Mono.just(
            ApiResponseDto
                .builder()
                .code(1)
                .message("I am "+message+".")
                .build()
        );
    }

    @GetMapping("/status/fallback")
    public Mono<ApiResponseDto> getStatusFallbackGateway(){
        return Mono.just(
            ApiResponseDto
                .builder()
                .code(1)
                .message("I am "+message+" fallback.")
                .build()
        );
    }
}
