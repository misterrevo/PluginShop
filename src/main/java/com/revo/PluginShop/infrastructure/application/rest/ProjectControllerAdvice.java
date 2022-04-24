package com.revo.PluginShop.infrastructure.application.rest;

import com.revo.PluginShop.domain.exception.EmailSendingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.constraints.Email;

@RestControllerAdvice
class ProjectControllerAdvice {

    @ExceptionHandler(EmailSendingException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    String emailSendingException(EmailSendingException exception){
        return exception.getMessage();
    }
}
