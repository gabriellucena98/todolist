package com.GB.todolist.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Tag(name = "users")
public class LoginRequestDto {

    @NotEmpty(message = "O username é obrigatório!")
    @NotNull(message = "O username é obrigatório!")
    @Schema(type = "string", example = "seuusername1")
    private String username;

    @NotEmpty(message = "O username é obrigatório!")
    @NotNull(message = "O username é obrigatório!")
    @Schema(type = "string", example = "suaSenhaMuitoForte!1")
    private String password;
}
