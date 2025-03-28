package com.pcoundia.products.domain.valueObject;

import com.pcoundia.products.domain.exception.PriceNotCorrect;

public class ProductPrice {

    private final double price;

    public ProductPrice(double price) {
        if (price < 0.0) {
            throw new PriceNotCorrect("Price not correct");
        }

        this.price = price;
    }

    public static ProductPrice create(double price) {
        return new ProductPrice(price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductPrice that)) return false;

        return Double.compare(that.price, this.price) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(price);
    }

    public double value() {
        return price;
    }

    @Override
    public String toString() {
        return String.valueOf(price);
    }
}
