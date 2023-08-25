package com.GB.todolist.usecase.impl;

import com.GB.todolist.controller.dto.GetUsersResponseDto;
import com.GB.todolist.controller.dto.RegisterRequestDto;
import com.GB.todolist.model.UserModel;
import com.GB.todolist.repository.UserModelRepository;
import com.GB.todolist.usecase.UserUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

//@Service
//@Slf4j
//public class UserUseCaseImpl implements UserUseCase {
//
//    @Autowired
//    private UserModelRepository userModelRepository;
//
//    @Override
//    public Long register(RegisterRequestDto registerRequestDto) {
//        UserModel userModel = UserModel
//                .builder()
//                .username(registerRequestDto.getUsername())
//                .password(registerRequestDto.getPassword())
//                .created(LocalDateTime.now())
//                .email(registerRequestDto.getEmail())
//                .build();
//        userModelRepository.save(userModel);
//
//        return userModel.getId();
//    }
//
//    @Override
//    public GetUsersResponseDto getUsers() {
//        List<UserModel> userModelList = userModelRepository.findAll();
//
//        return GetUsersResponseDto
//                .builder()
//                .userModelList(userModelList)
//                .build();
//    }
//}
