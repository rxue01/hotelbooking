package com.laioffer.staybooking.exception;

import javax.transaction.Transactional;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message) {
        super(message);
    }


    }

