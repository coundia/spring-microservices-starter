package com.pcoundia.products.application.projections;

import com.pcoundia.products.application.event.ProductCreatedEvent;
import com.pcoundia.products.application.event.ProductDeletedEvent;
import com.pcoundia.products.application.event.UpdateNameProductEvent;
import com.pcoundia.products.domain.exception.ProductNotFoundException;
import com.pcoundia.products.infrastructure.entity.Product;
import com.pcoundia.products.infrastructure.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Component
public class ProductProjection {

    private final ProductRepository productRepository;

    public ProductProjection(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventHandler
    public void on(ProductCreatedEvent event) {
        productRepository.insert(event.getId(), event.getName(), event.getPrice())
            .subscribe(
                inserted -> log.info("Product inserted: {}", inserted),
                error -> log.error("Error saving product: {}", error.getMessage(), error)
            );
    }

    @EventHandler
    public void on(ProductDeletedEvent event) {
        log.info("Received ProductDeletedEvent for id: {}", event.getProductId());
        productRepository.deleteById(event.getProductId())
            .doOnSuccess(unused -> log.info("Product deleted successfully: {}", event.getProductId()))
            .doOnError(error -> log.error("Error deleting product: {}", error.getMessage(), error))
            .subscribe();
    }

    @EventHandler
    public void on(UpdateNameProductEvent event) {
        log.info("Received UpdateNameProductEvent for id: {}, new name {}", event.getProductId(),event.getProductName());
        productRepository.updateNameById(event.getProductId(), event.getProductName())
            .doOnSuccess(unused -> log.info("Product updated successfully: {}", event.getProductId()))
            .doOnError(error -> log.error("Error updating product: {}", error.getMessage(), error))
            .subscribe();
    }



}
