package com.GB.todolist.exception;

import com.GB.todolist.exception.entity.ErrorData;
import lombok.Getter;

import java.util.HashSet;

@Getter
public class RequestGenericException extends TodoListServiceException{

    private final ErrorData erro;

    public RequestGenericException(String code, String title, String message, int statusCode) {
        super(new HashSet<>(), statusCode);
        this.erro = ErrorData
                .builder()
                .code(code)
                .detail(message)
                .title(title)
                .build();
    }
}
