package com.quickcart.order.exception;

public class ConflictException
        extends RuntimeException {

    public ConflictException(
            String message) {

        super(message);
    }
}