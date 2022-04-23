package com.revo.PluginShop.infrastructure.application.rest;

import com.revo.PluginShop.MysqlContainer;
import com.revo.PluginShop.domain.port.JwtPort;
import com.revo.PluginShop.infrastructure.application.rest.dto.UserRestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;

import static com.revo.PluginShop.MysqlContainer.APPLICATION_JSON_UTF8;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class UserControllerTest extends MysqlContainer {

    private static final String GET_END_POINT = "/users/payment@email.pl";
    private static final String EMAIL_JSON_PATH = "$.email";
    private static final String GET_EMAIL = "payment@email.pl";
    private static final String NOT_EXISTS_EMAIL = "nonexists@email.pl";
    private static final String THROW_GET_END_POINT = "/users/nonexists@email.pl";
    private static final String USERS_END_POINT = "/users";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String CREATED_EMAIL = "CREATED@EMAIL.PL";
    private static final String CREATED_PASSWORD = "CREATEDcreated1!";
    private static final String CHANGE_BLOCK_END_POINT = "/users/tolock@email.pl/1";
    private static final String LOCKED_JSON_PATH = "$.locked";
    private static final String THROW_CHANGE_BLOCK_END_POINT = "/users/nonexists@email.pl/1";
    private static final String LOCK_EMAIL = "tolock@email.pl";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtPort jwtPort;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(roles = ROLE_ADMIN)
    void shouldGetUserByEmail() throws Exception{
        //given
        //when
        //then
        mockMvc.perform(get(URI.create(GET_END_POINT)))
                .andExpect(jsonPath(EMAIL_JSON_PATH).value(GET_EMAIL))
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser(roles = ROLE_ADMIN)
    void shouldThrowWhileGettingUserByEmail() throws Exception{
        //given
        //when
        //then
        mockMvc.perform(get(URI.create(THROW_GET_END_POINT)))
                .andExpect(status().is(404));
    }

    @Test
    void shouldGetUserByToken() throws Exception{
        //given
        var token = jwtPort.getToken(GET_EMAIL);
        //when
        //then
        mockMvc.perform(get(USERS_END_POINT)
                        .header(AUTHORIZATION_HEADER, token))
                .andExpect(jsonPath(EMAIL_JSON_PATH).value(GET_EMAIL))
                .andExpect(status().is(200));
    }

    @Test
    void shouldThrowWhileGettingUserByToken() throws Exception{
        //given
        var token = jwtPort.getToken(NOT_EXISTS_EMAIL);
        //when
        //then
        mockMvc.perform(get(USERS_END_POINT)
                        .header(AUTHORIZATION_HEADER, token))
                .andExpect(status().is(404));
    }

    @Test
    void shouldCreateNewUser() throws Exception{
        //given
        var userRestDto = UserRestDto.builder()
                .email(CREATED_EMAIL)
                .password(CREATED_PASSWORD)
                .build();
        var jsonString = objectMapper.writeValueAsString(userRestDto);
        //when
        //then
        mockMvc.perform(post(URI.create(USERS_END_POINT))
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonString))
                .andExpect(jsonPath(EMAIL_JSON_PATH).value(CREATED_EMAIL))
                .andExpect(status().is(201));
    }

    @Test
    void shouldThrowWhileCreatingNewUser() throws Exception{
        //given
        var userRestDto = UserRestDto.builder()
                .email(GET_EMAIL)
                .password(CREATED_PASSWORD)
                .build();
        var jsonString = objectMapper.writeValueAsString(userRestDto);
        //when
        //then
        mockMvc.perform(post(URI.create(USERS_END_POINT))
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(jsonString))
                .andExpect(status().is(400));
    }

    @Test
    @WithMockUser(roles = ROLE_ADMIN)
    void shouldChangeBlockStatus() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(put(URI.create(CHANGE_BLOCK_END_POINT)))
                .andExpect(jsonPath(EMAIL_JSON_PATH).value(LOCK_EMAIL))
                .andExpect(jsonPath(LOCKED_JSON_PATH).value(true))
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser(roles = ROLE_ADMIN)
    void shouldThrowChangingBlockStatus() throws Exception{
        //given
        //when
        //then
        mockMvc.perform(put(URI.create(THROW_CHANGE_BLOCK_END_POINT)))
                .andExpect(status().is(404));
    }
}