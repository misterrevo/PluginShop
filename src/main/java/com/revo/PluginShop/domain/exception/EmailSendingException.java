package com.revo.PluginShop.domain.exception;

public class EmailSendingException extends RuntimeException {

    private static final String MESSAGE = "Error while sending email to %s!";

    public EmailSendingException(String to) {
        super(MESSAGE.formatted(to));
    }
}
