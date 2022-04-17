package com.revo.PluginShop.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revo.PluginShop.infrastructure.application.rest.dto.UserCredentialsDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private static final String PROCCESS_URL = "/login";
    private static final String AUTHENTICATION_EXCEPTION_MESSAGE = "Something went wrong while parsing /login request body";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public LoginFilter(AuthenticationManager authenticationManager, AuthenticationFailureHandler loginFailureHandler, AuthenticationSuccessHandler loginSuccessHandler) {
        this.setAuthenticationSuccessHandler(loginSuccessHandler);
        this.setAuthenticationFailureHandler(loginFailureHandler);
        this.setAuthenticationManager(authenticationManager);
        this.setFilterProcessesUrl(PROCCESS_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            UserCredentialsDto credentials = mapFromJson(request);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());
            setDetails(request, token);
            return authenticate(token);
        } catch (IOException exception) {
            throw new InternalAuthenticationServiceException(AUTHENTICATION_EXCEPTION_MESSAGE, exception);
        }
    }

    private Authentication authenticate(UsernamePasswordAuthenticationToken token){
        return getAuthenticationManager().authenticate(token);
    }

    private UserCredentialsDto mapFromJson(HttpServletRequest request) throws IOException {
        return objectMapper.readValue(request.getInputStream(), UserCredentialsDto.class);
    }
}
