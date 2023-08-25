package com.GB.todolist.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@Slf4j
public class JWTService {

    @Value("${spring.security.jwt.secret}")
    private String secret;

    @Value("${spring.security.jwt.expiration}")
    private Long expiration;

    public String createToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }

    public boolean isValidToken(String token) {
        if (token != null) {
            Claims claims = getClaims(token);
            if (claims != null) {
                String username = claims.getSubject();
                Date expirationDate = claims.getExpiration();
                Date now = new Date(System.currentTimeMillis());
                return username != null && expirationDate != null && now.before(expirationDate);
            }
        }
        log.info("Token inválido ou nulo!");
        return false;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.error("O parse não foi feito por alguma razão!");
            return null;
        }
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }
}
