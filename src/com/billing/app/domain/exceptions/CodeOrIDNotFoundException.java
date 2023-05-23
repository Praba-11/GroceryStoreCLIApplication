package com.billing.app.domain.exceptions;

public class CodeOrIDNotFoundException extends Exception {
    public CodeOrIDNotFoundException(String message) {
        super(message);
    }
}