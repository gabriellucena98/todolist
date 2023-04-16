package com.GB.todolist.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponseWithIdDto {

    private Long id;
    private String code;
    private String title;
    private String detail;
}
