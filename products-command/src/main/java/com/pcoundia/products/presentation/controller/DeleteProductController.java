package com.pcoundia.products.presentation.controller;

import com.pcoundia.products.application.command.DeleteProductCommand;
import com.pcoundia.products.application.dto.ProductResponseDTO;
import com.pcoundia.products.application.query.FindByIdProductQuery;
import com.pcoundia.products.domain.exception.ProductNotFoundException;
import com.pcoundia.products.domain.valueObject.ProductId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api/v1/commands/products")
public class DeleteProductController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public DeleteProductController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete a product by id")
    public CompletableFuture<ResponseEntity<String>> deleteProduct(@PathVariable String id) {
        if (id == null || id.isEmpty()) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Invalid id"));
        }
        log.info("Deleting product with id: {}", id);
        ProductId productId = ProductId.create(id);

        return Mono.fromFuture(() -> queryGateway.query(
                new FindByIdProductQuery(productId), ProductResponseDTO.class))
            .subscribeOn(Schedulers.boundedElastic())
            .flatMap(foundProduct -> {
                if (foundProduct == null) {
                    return Mono.error(new ProductNotFoundException("Product not found with id: " + id));
                }
                return Mono.fromFuture(() -> commandGateway.send(new DeleteProductCommand(productId)))
                    .subscribeOn(Schedulers.boundedElastic());
            })
            .map(result -> ResponseEntity.ok("Product deleted successfully"))
            .onErrorResume(ProductNotFoundException.class, ex -> {
                log.info("Product not found: {}", ex.getMessage());
                return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()));
            })
            .onErrorResume(ex -> {
                log.error("Error deleting product with id {}: {}", id, ex.getMessage(), ex);
                return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting product: " + ex.getMessage()));
            })
            .toFuture();
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> productNotFoundExceptionHandler(ProductNotFoundException e) {
        log.info("Product not found: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
