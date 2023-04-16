package com.GB.todolist.exception;


import com.GB.todolist.exception.entity.ErrorData;

import java.util.Set;

public class TodoListServiceException extends RuntimeException{

    private final int statusCode;

    private final Set<ErrorData> error;
    public TodoListServiceException(Set<ErrorData> error, int statusCode) {
        this.statusCode = statusCode;
        this.error = error;
    }

    public int getStatusCode() { return statusCode; }

    public Set<ErrorData> getError() { return this.error;}
}
