package com.pcoundia.products.presentation.controller;

import com.pcoundia.products.application.dto.ListProductResponseDTO;
import com.pcoundia.products.application.query.FindByNameProductsQuery;
import com.pcoundia.products.domain.exception.ProductNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@RequestMapping("/api/v1/queries/products/name")
public class FindByNameProductController {

    private final QueryGateway queryGateway;

    public FindByNameProductController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    @Operation(description = "Find product by name")
    public Mono<ResponseEntity<ListProductResponseDTO>> findByNameProduct(
        @RequestParam String name,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int limit) {

        if (name == null || name.isEmpty()) {
            return Mono.error(new IllegalArgumentException("Name cannot be null or empty"));
        }

        return Mono.fromFuture(() -> queryGateway.query(
                new FindByNameProductsQuery(page, limit, name),
                ListProductResponseDTO.class))
            .subscribeOn(Schedulers.boundedElastic())
            .flatMap(response -> {
                if (response.getProducts().isEmpty()) {
                    return Mono.error(new ProductNotFoundException("No products with '" + name + "' found"));
                }
                return Mono.just(ResponseEntity.ok(response));
            })
            .onErrorResume(ProductNotFoundException.class, ex ->
                Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)))
            .onErrorResume(ex ->
                Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> productNotFoundExceptionHandler(ProductNotFoundException e) {
        log.info("Product not found: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
