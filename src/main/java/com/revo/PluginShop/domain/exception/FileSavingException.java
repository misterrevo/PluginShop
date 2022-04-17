package com.revo.PluginShop.domain.exception;

public class FileSavingException extends RuntimeException {

    private static final String MESSAGE = "Error while saving file on server!";

    public FileSavingException(){
        super(MESSAGE);
    }
}
