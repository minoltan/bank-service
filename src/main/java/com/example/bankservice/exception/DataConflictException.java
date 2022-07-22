package com.example.bankservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value= HttpStatus.CONFLICT)
public class DataConflictException extends RuntimeException{

    public DataConflictException(String message) {
        super(message);
    }

    public DataConflictException(Exception exception) {
        super(exception);
    }

    public DataConflictException(String message, Exception e) {
        super(message, e);
    }
}
