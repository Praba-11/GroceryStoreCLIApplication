package com.billing.app.domain.exceptions;

public class InvalidConstraintLengthException extends ProductException {
    public InvalidConstraintLengthException (String message) {
        super(message);
    }
}
