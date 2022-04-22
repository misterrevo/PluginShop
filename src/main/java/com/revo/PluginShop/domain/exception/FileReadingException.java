package com.revo.PluginShop.domain.exception;

public class FileReadingException extends RuntimeException {

    private static final String MESSAGE = "Error while reading file %s !";

    public FileReadingException(String file) {
        super(MESSAGE.formatted(file));
    }
}
