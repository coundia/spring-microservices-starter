package com.pcoundia.products.domain.useCase;

import com.pcoundia.products.domain.valueObject.ProductName;
import com.pcoundia.products.domain.valueObject.ProductPrice;

public interface AddProductInterface {
    void addProduct(ProductPrice price, ProductName name);
}
