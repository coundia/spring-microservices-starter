package com.pcoundia.products.application.queryHandler;

import com.pcoundia.products.application.dto.ListProductResponseDTO;
import com.pcoundia.products.application.query.FindByNameProductsQuery;
import com.pcoundia.products.application.query.ListProductsQuery;
import com.pcoundia.products.infrastructure.entity.Product;
import com.pcoundia.products.infrastructure.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class ListProductsQueryHandler {

    private final ProductRepository productRepository;

    public ListProductsQueryHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryHandler
    public Mono<ListProductResponseDTO> handle(ListProductsQuery query) {
        int limit = (int) query.getLimit();
        int offset = (int) (query.getPage() * query.getLimit());
        Mono<Long> totalElementsMono = productRepository.findAll().count();
        Mono<List<Product>> productsMono = productRepository
            .findAllByPage(limit, offset)
            .collectList();
        return Mono.zip(productsMono, totalElementsMono)
            .map(tuple -> ListProductResponseDTO.from(tuple.getT1(), query.getPage(), query.getLimit(), tuple.getT2()));
    }


}
