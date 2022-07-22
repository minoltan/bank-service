package com.example.bankservice.exception;

import com.example.bankservice.response.MyResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


import javax.servlet.http.HttpServletRequest;



@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(DataConflictException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public @ResponseBody
    MyResponse handleConflictException(final DataConflictException exception,
                                       final HttpServletRequest request) {
        MyResponse myResponse = new MyResponse();
        myResponse.setSuccess(false);
        myResponse.setErrorMessage(exception.getMessage());
        myResponse.setRequestedURI(request.getRequestURI());
        myResponse.setStatusInfo(HttpStatus.CONFLICT.getReasonPhrase());
        return myResponse;
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody
    MyResponse handleIMINotFoundException(final DataNotFoundException exception,
                                           final HttpServletRequest request) {
        MyResponse myResponse = new MyResponse();
        myResponse.setSuccess(false);
        myResponse.setErrorMessage(exception.getMessage());
        myResponse.setRequestedURI(request.getRequestURI());
        myResponse.setStatusInfo(HttpStatus.NOT_FOUND.getReasonPhrase());
        return myResponse;
    }


}

