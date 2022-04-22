package com.revo.PluginShop.domain.exception;

public class UserDoesNotHavePlugin extends RuntimeException {

    private static final String MESSAGE = "Error while getting resource of plugin with id %d, user %s don't have it in library!";

    public UserDoesNotHavePlugin(Long id, String email) {
        super(MESSAGE.formatted(id, email));
    }
}
