package com.revo.PluginShop.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.revo.PluginShop.domain.port.JwtPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.Date;

@Component
@RequiredArgsConstructor
class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final String HEADER_NAME = "Authorization";
    private static final String HEADER_VALUE = "Bearer %s";

    private final JwtPort jwtPort;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        String token = createToken(authentication);
        response.setHeader(HEADER_NAME, HEADER_VALUE.formatted(token));
    }

    private String createToken(Authentication authentication){
        var principal = authentication.getPrincipal();
        return jwtPort.getToken(principal.toString());
    }
}
