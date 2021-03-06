package com.revo.PluginShop.infrastructure.application.rest;

import com.revo.PluginShop.domain.exception.UserDoesNotExistsException;
import com.revo.PluginShop.domain.exception.UserNameInUseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class UserControllerAdvice {

    @ExceptionHandler(UserDoesNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userDoesNotExistsException(UserDoesNotExistsException exception){
        return exception.getMessage();
    }

    @ExceptionHandler(UserNameInUseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String userNameInUseException(UserNameInUseException exception){
        return exception.getMessage();
    }
}
