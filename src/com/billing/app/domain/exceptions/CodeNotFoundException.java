package com.billing.app.domain.exceptions;

public class CodeNotFoundException extends Exception {
    public CodeNotFoundException (String message) {
        super(message);
    }
}
