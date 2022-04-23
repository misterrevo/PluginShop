package com.revo.PluginShop.domain.exception;

public class FileReadingException extends RuntimeException {

    private static final String MESSAGE = "Error while reading file %s from path %s!";

    public FileReadingException(String file, String path) {
        super(MESSAGE.formatted(file, path));
    }
}
