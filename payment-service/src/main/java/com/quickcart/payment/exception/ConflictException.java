package com.quickcart.payment.exception;

public class ConflictException
        extends RuntimeException {

    public ConflictException(
            String message) {

        super(message);
    }
}