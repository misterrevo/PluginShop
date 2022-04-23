package com.revo.PluginShop.infrastructure.application.rest;

import com.revo.PluginShop.MysqlContainer;
import com.revo.PluginShop.domain.port.JwtPort;
import com.revo.PluginShop.infrastructure.application.rest.dto.UserRestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;

import static com.revo.PluginShop.MysqlContainer.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class LoginControllerTest extends MysqlContainer {

    private static final String LOGIN_EMAIL = "payment@email.pl";
    private static final String LOGIN_PASSWORD = "testTest1!";
    private static final String LOGIN_END_POINT = "/login";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String NOT_EXISTS_EMAIL = "notexists@email.pl";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtPort jwtPort;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldLogin() throws Exception{
        //given
        var userRestDto = UserRestDto.builder()
                .email(LOGIN_EMAIL)
                .password(LOGIN_PASSWORD)
                .build();
        var jsonString = objectMapper.writeValueAsString(userRestDto);
        //when
        //then
        mockMvc.perform(post(URI.create(LOGIN_END_POINT))
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonString))
                .andExpect(header().exists(AUTHORIZATION_HEADER))
                .andExpect(status().is(200));
    }

    @Test
    void shouldThrowWhileLogging() throws Exception{
        //given
        var userRestDto = UserRestDto.builder()
                .email(NOT_EXISTS_EMAIL)
                .password(LOGIN_PASSWORD)
                .build();
        var jsonString = objectMapper.writeValueAsString(userRestDto);
        //when
        //then
        mockMvc.perform(post(URI.create(LOGIN_END_POINT))
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(jsonString))
                .andExpect(status().is(401));
    }
}