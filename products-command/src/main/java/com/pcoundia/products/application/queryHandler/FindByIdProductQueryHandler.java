package com.pcoundia.products.application.queryHandler;

import com.pcoundia.products.application.dto.ProductResponseDTO;
import com.pcoundia.products.application.query.FindByIdProductQuery;
import com.pcoundia.products.domain.exception.ProductNotFoundException;
import com.pcoundia.products.infrastructure.repository.ProductRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class FindByIdProductQueryHandler {

    private final ProductRepository productRepository;

    public FindByIdProductQueryHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryHandler
    public Mono<ProductResponseDTO> handle(FindByIdProductQuery query) {
        return productRepository.existsById(query.getProductId().value())
            .flatMap(exists -> {
                if (exists) {
                    return productRepository.findById(query.getProductId().value())
                        .map(product -> new ProductResponseDTO(
                            product.getId(),
                            product.getName(),
                            product.getPrice()
                        ));
                } else {
                    return Mono.error(new ProductNotFoundException("Product not found with id: " + query.getProductId().value()));
                }
            });
    }
}
