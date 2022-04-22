package com.revo.PluginShop.infrastructure.application.rest;

import com.revo.PluginShop.domain.exception.FileSavingException;
import com.revo.PluginShop.domain.exception.PluginDoesNotExistsException;
import com.revo.PluginShop.domain.exception.VersionDoesNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class PluginControllerAdvice {

    @ExceptionHandler(FileSavingException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    String fileSavingException(FileSavingException exception){
        return exception.getMessage();
    }

    @ExceptionHandler(PluginDoesNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String pluginDoesNotExistsException(PluginDoesNotExistsException exception){
        return exception.getMessage();
    }

    @ExceptionHandler(VersionDoesNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String pluginDoesNotExistsException(VersionDoesNotExistsException exception){
        return exception.getMessage();
    }
}
