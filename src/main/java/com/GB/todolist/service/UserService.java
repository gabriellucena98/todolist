package com.GB.todolist.service;

import com.GB.todolist.controller.dto.GetUsersResponseDto;
import com.GB.todolist.controller.dto.RegisterRequestDto;
import com.GB.todolist.exception.UserNotFoundException;
import com.GB.todolist.model.UserModel;
import com.GB.todolist.repository.UserModelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserModelRepository repository;

    public Long register(RegisterRequestDto registerRequestDto) {
        UserModel userModel = UserModel
                .builder()
                .username(registerRequestDto.getUsername())
                .password(new BCryptPasswordEncoder().encode(registerRequestDto.getPassword()))
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .email(registerRequestDto.getEmail())
                .build();
        repository.save(userModel);

        return userModel.getId();
    }

    public GetUsersResponseDto getUsers() {
        List<UserModel> userModelList = repository.findAll();

        return GetUsersResponseDto
                .builder()
                .userModelList(userModelList)
                .build();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).orElseThrow(() -> {
            log.error("Usuário não encontrado pelo username");
            throw new UserNotFoundException("Usuário não encontrado!");
        });
    }

    public UserModel setUserToken(String username, String token) {
        UserModel userModel = (UserModel) loadUserByUsername(username);
        userModel.setToken(token);
        userModel.setLastLogin(LocalDateTime.now());
        userModel.setModified(LocalDateTime.now());
        return repository.save(userModel);
    }

    public UserModel getAuthenticatedUser() {
        return (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
