package com.pcoundia.products.domain.valueObject;

public class ProductName {
    private final String name;

    public ProductName(String name) {
        this.name = name;
    }

    public static ProductName create(String name) {
        return new ProductName(name);
    }

    public String value() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductName)) return false;
        return name.equals(((ProductName) o).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

}
