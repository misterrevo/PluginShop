package com.revo.PluginShop.domain.exception;

public class PluginAddException extends RuntimeException {

    private static final String MESSAGE = "Error while adding plugin to user library, user had it before!";

    public PluginAddException(Long id) {
        super(MESSAGE.formatted(id));
    }
}
