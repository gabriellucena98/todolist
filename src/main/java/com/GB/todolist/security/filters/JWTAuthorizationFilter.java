package com.GB.todolist.security.filters;

import com.GB.todolist.exception.RequestGenericException;
import com.GB.todolist.service.JWTService;
import com.GB.todolist.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private JWTService jwtService;

    private UserService userService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTService jwtService, UserService userService) {
        super(authenticationManager);
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            String token = authorizationHeader.substring("Bearer".length());
            UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(token);
            if (authenticationToken != null) {
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (jwtService.isValidToken(token)) {
            String username = jwtService.getUsername(token);
            UserDetails userDetails = userService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }
        log.info("Não está autorizado ou não tem token");
        throw new RequestGenericException("403", "Você não tem permissão", "Token não autorizado ou nulo", 403);
    }
}
