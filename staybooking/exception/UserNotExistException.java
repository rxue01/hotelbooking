package com.laioffer.staybooking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

public class UserNotExistException extends RuntimeException {
    public UserNotExistException(String message) {
        super(message);
    }

}
