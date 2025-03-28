package com.pcoundia.products.domain.exception;

public class PriceNotCorrect extends RuntimeException implements PriceNotCorrectInterface {
    public PriceNotCorrect(String message) {
        super(message);
    }
}
