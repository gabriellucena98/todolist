package com.GB.todolist.controller;

import com.GB.todolist.controller.dto.GenericResponseWithIdDto;
import com.GB.todolist.controller.dto.GetUsersResponseDto;
import com.GB.todolist.controller.dto.RegisterRequestDto;
import com.GB.todolist.service.UserService;
import com.GB.todolist.usecase.UserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponseWithIdDto> register(@RequestBody RegisterRequestDto registerRequestDto) {
        Long userId = userService.register(registerRequestDto);
        return new ResponseEntity(GenericResponseWithIdDto
                .builder()
                .code("201")
                .title("/register")
                .detail("Usuário criado com sucesso!")
                .id(userId)
                .build(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetUsersResponseDto> getUsers() {
        GetUsersResponseDto getUsersResponseDto = userService.getUsers();
        return ResponseEntity.ok(getUsersResponseDto);
    }

}
