package com.billing.app.domain.exceptions;

public class CodeNotFoundException extends ProductException {
    public CodeNotFoundException (String message) {
        super(message);
    }
}