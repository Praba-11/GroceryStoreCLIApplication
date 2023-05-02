package com.billing.app.domain.exceptions;

public class ProductNullConstraintException extends ProductException {
    public ProductNullConstraintException(String message) {
        super(message);
    }
}
