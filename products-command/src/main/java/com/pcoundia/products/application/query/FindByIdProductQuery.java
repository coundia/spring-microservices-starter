package com.pcoundia.products.application.query;

import com.pcoundia.products.domain.valueObject.ProductId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindByIdProductQuery {

    private ProductId productId;
}
