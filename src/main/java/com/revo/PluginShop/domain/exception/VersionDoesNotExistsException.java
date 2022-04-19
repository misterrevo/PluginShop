package com.revo.PluginShop.domain.exception;

public class VersionDoesNotExistsException extends RuntimeException {

    private static final String MESSAGE = "Erorr while getting version, probably version with id %d not exists!";

    public VersionDoesNotExistsException(Long id) {
        super(MESSAGE.formatted(id));
    }
}
