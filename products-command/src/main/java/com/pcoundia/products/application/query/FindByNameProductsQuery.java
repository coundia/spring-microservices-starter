package com.pcoundia.products.application.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor
@Getter
public class FindByNameProductsQuery {
    private long page;
    private long limit;
    private String name;
}
