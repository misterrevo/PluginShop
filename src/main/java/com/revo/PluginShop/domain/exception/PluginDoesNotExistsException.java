package com.revo.PluginShop.domain.exception;

public class PluginDoesNotExistsException extends RuntimeException {

    private static final String MESSAGE = "Error while getting plugin, probably plugin with id %d not exists in base!";

    public PluginDoesNotExistsException(Long id) {
        super(MESSAGE.formatted(id));
    }
}
