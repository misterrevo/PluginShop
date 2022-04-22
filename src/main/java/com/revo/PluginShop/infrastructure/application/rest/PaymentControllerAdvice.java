package com.revo.PluginShop.infrastructure.application.rest;

import com.revo.PluginShop.domain.exception.PaymentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class PaymentControllerAdvice {

    @ExceptionHandler(PaymentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String paymentException(PaymentException exception){
        return exception.getMessage();
    }
}
