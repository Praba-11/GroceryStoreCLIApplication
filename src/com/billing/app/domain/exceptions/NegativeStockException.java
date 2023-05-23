package com.billing.app.domain.exceptions;

public class NegativeStockException extends  Exception {
    public NegativeStockException(String message) {
        super(message);
    }
}