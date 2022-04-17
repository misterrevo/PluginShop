package com.revo.PluginShop.infrastructure.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.revo.PluginShop.domain.port.JwtPort;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Date;

class JwtAdapter implements JwtPort {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String TOKEN_REPLACEMENT = "";

    @Value("${spring.security.jwt.secret}")
    private String secret;
    @Value("${spring.security.jwt.expirationTime}")
    private Long expirationTime;

    @Override
    public String getEmailFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token.replace(TOKEN_PREFIX, TOKEN_REPLACEMENT))
                .getSubject();
    }

    @Override
    public String createTokenByEmail(String email) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC256(secret));
    }
}
