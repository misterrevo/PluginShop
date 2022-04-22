package com.revo.PluginShop.infrastructure.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.revo.PluginShop.domain.port.JwtPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
class JwtAdapter implements JwtPort {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String EMPTY_STRING = "";

    @Value("${spring.security.jwt.secret}")
    private String secret;
    @Value("${spring.security.jwt.expirationTime}")
    private long expirationTime;

    @Override
    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token.replace(TOKEN_PREFIX, EMPTY_STRING))
                .getSubject();
    }

    @Override
    public String getToken(String subject) {
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC256(secret));
    }
}
