package com.revo.PluginShop.domain.exception;

public class UserNameInUseException extends RuntimeException {

    private static final String MESSAGE = "Error while creating user, probably %s is in use!";

    public UserNameInUseException(String email) {
        super(MESSAGE.formatted(email));
    }
}
