package com.pcoundia.products.presentation.controller;

import com.pcoundia.products.application.Mapper.ProductMapper;
import com.pcoundia.products.application.command.CreateProductCommand;
import com.pcoundia.products.application.dto.ProductRequestDTO;
import com.pcoundia.products.application.dto.ProductResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/commands/products")
@Tag(name = "Product Controller", description = "Endpoints for managing products")
@Slf4j
public class AddProductController {

    private final CommandGateway commandGateway;

    public AddProductController(@Lazy CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("")
    @Operation(summary = "Add a new product", description = "Creates a new product and returns the created entity")
    public CompletableFuture<ResponseEntity<ProductResponseDTO>> addProduct(@RequestBody ProductRequestDTO productRequestDTO) {

        CreateProductCommand createProductCommand = ProductMapper.toCommand(productRequestDTO);

        return commandGateway.send(createProductCommand)
                .thenApply(
                        productId -> {
                            log.info("Product created successfully with ID: {}", productId);
                            ProductResponseDTO productResponseDTO = new ProductResponseDTO(
                                    productId.toString(),
                                    productRequestDTO.getName(),
                                    productRequestDTO.getPrice()
                            );
                            return ResponseEntity.ok(productResponseDTO);
                        })
                .exceptionally(ex -> {
                    log.error("Failed to create product: {}", ex.getMessage());
                   // ex.printStackTrace();
                    return ResponseEntity.internalServerError().build();
                });
    }
}
