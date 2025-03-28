package com.pcoundia.products.presentation.controller;

import com.pcoundia.products.application.dto.ListProductResponseDTO;
import com.pcoundia.products.application.query.ListProductsQuery;
import com.pcoundia.products.domain.exception.ProductNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@RequestMapping("/api/v1/queries/products")
public class ListProductController {

    private final QueryGateway queryGateway;

    public ListProductController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ListProductResponseDTO>> listProduct(
        @RequestParam(defaultValue = "0") long page,
        @RequestParam(defaultValue = "10") long limit
    ) {
        return Mono
            .fromFuture(() -> queryGateway.query(
                new ListProductsQuery(page, limit),
                ListProductResponseDTO.class
            ))
            .subscribeOn(Schedulers.boundedElastic())
            .map(response -> {
                if (response.getProducts().isEmpty()) {
                    throw new ProductNotFoundException("No products found");
                }
                return ResponseEntity.ok(response);
            })
            .onErrorResume(ProductNotFoundException.class, ex -> {
                log.info("Product not found: {}", ex.getMessage());
                return Mono.just(ResponseEntity.notFound().build());
            })
            .onErrorResume(ex -> {
                log.error("Unexpected error: {}", ex.getMessage(), ex);
                return Mono.just(ResponseEntity.internalServerError().build());
            });
    }

}
