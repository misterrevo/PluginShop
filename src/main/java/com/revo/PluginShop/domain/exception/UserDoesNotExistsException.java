package com.revo.PluginShop.domain.exception;

public class UserDoesNotExistsException extends RuntimeException {

    private static final String MESSAGE = "Error while getting user, probably user with email %s does not exists in base!";

    public UserDoesNotExistsException(String email) {
        super(MESSAGE.formatted(email));
    }
}
