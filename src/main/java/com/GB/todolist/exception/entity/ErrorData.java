package com.GB.todolist.exception.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorData {

    private static final long serialVersionUID = 1L;

    private String code;
    private String title;
    private String detail;
}
