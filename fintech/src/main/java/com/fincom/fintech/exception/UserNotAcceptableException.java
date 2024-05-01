package com.fincom.fintech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UserNotAcceptableException extends RuntimeException{

    public UserNotAcceptableException(String message) {
        super(message);
    }
}
