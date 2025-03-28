package com.pcoundia.products.application.command;

import com.pcoundia.products.domain.valueObject.ProductId;
import com.pcoundia.products.domain.valueObject.ProductName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateNameProductCommand {
    private ProductId productId;
    private ProductName productName;
}
