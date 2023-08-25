package com.GB.todolist.exception;

import com.GB.todolist.exception.entity.ErrorData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RuntimeExceptionHandler {

    @ExceptionHandler(RequestGenericException.class)
    public ResponseEntity<ErrorData> handlerRequestGenericException(RequestGenericException ex) {

        return new ResponseEntity<>(ex.getErro(), HttpStatus.valueOf(ex.getStatusCode()));
    }
}
