package com.GB.todolist.security.filters;

import com.GB.todolist.controller.dto.LoginRequestDto;
import com.GB.todolist.controller.dto.RegisterRequestDto;
import com.GB.todolist.exception.AuthenticationException;
import com.GB.todolist.mapper.JSONMapper;
import com.GB.todolist.model.UserModel;
import com.GB.todolist.service.JWTService;
import com.GB.todolist.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private JWTService jwtService;

    private UserService userService;

    public JWTAuthenticationFilter(
            AuthenticationManager authenticationManager,
            JWTService jwtService,
            UserService userService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        Authentication authentication;
        try {
            LoginRequestDto loginRequestDto = new ObjectMapper().readValue(
                    request.getInputStream(),
                    LoginRequestDto.class
            );
            userService.loadUserByUsername(loginRequestDto.getUsername());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword(), new ArrayList<>());
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            throw new AuthenticationException("A autenticação do usuário falhou" + e.getMessage());
        }
        return authentication;
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        String username = ((UserModel) authResult.getPrincipal()).getUsername();
        String token = jwtService.createToken(username);
        UserModel userModel = userService.setUserToken(username, token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String userResponseBody = JSONMapper.objectToJSON(userModel);

        response.getWriter().append(userResponseBody);
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            org.springframework.security.core.AuthenticationException failed) throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().append("{\"message\": \"Usuario ou senha invalidos\"}");
    }
}
