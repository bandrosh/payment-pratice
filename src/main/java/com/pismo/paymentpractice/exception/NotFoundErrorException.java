package com.pismo.paymentpractice.exception;

public class NotFoundErrorException extends RuntimeException {
    public NotFoundErrorException(String message) {
        super(message);
    }
}
