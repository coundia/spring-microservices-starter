package com.pcoundia.products.presentation.controller;

import com.pcoundia.products.application.command.UpdateNameProductCommand;
import com.pcoundia.products.application.dto.ProductRequestDTO;
import com.pcoundia.products.application.dto.ProductResponseDTO;
import com.pcoundia.products.application.query.FindByIdProductQuery;
import com.pcoundia.products.domain.exception.ProductNotFoundException;
import com.pcoundia.products.domain.valueObject.ProductId;
import com.pcoundia.products.domain.valueObject.ProductName;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@RequestMapping("/api/v1/commands/products")
@Tag(name = "Product Controller", description = "Endpoints for managing products")
public class UpdateNameProductController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public UpdateNameProductController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    @Operation(summary = "Update product name", description = "Updates the name of a product")
    public Mono<ResponseEntity<String>> updateNameProduct(
        @RequestBody ProductRequestDTO productRequestDTO,
        @PathVariable String id
    ) {
        if (id == null || id.trim().isEmpty()) {
            return Mono.just(ResponseEntity.badRequest().body("Invalid product ID"));
        }
        if (productRequestDTO.getName() == null || productRequestDTO.getName().trim().isEmpty()) {
            return Mono.just(ResponseEntity.badRequest().body("Product name cannot be empty"));
        }

        log.info("Updating product name: id={}, newName={}", id, productRequestDTO.getName());
        ProductId productId = ProductId.create(id);
        ProductName productName = ProductName.create(productRequestDTO.getName());

        return Mono.fromFuture(() ->
                queryGateway.query(new FindByIdProductQuery(productId), ProductResponseDTO.class))
            .subscribeOn(Schedulers.boundedElastic())
            .flatMap(productResponse -> {
                if (productResponse == null) {
                    log.warn("Product not found: id={}", id);
                    return Mono.error(new ProductNotFoundException("Product not found with id: " + id));
                }
                return Mono.fromFuture(() ->
                        commandGateway.send(new UpdateNameProductCommand(productId, productName)))
                    .subscribeOn(Schedulers.boundedElastic());
            })
            .map(result -> {
                log.info("Product updated successfully: id={}", id);
                return ResponseEntity.ok("Product updated successfully");
            })
            .onErrorResume(ProductNotFoundException.class, ex -> {
                log.info("Product not found: {}", ex.getMessage());
                return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()));
            })
            .onErrorResume(ex -> {
                log.error("Error updating product: id={}, error={}", id, ex.getMessage(), ex);
                return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating product: " + ex.getMessage()));
            });
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> productNotFoundExceptionHandler(ProductNotFoundException e) {
        log.info("Product not found: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
