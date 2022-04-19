package com.revo.PluginShop.domain.exception;

public class PaymentException extends RuntimeException{

    private static final String MESSAGE = "Error while processing payment with id %s!";

    public PaymentException(String id) {
        super(MESSAGE.formatted(id));
    }
}
