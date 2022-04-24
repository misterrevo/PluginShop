package com.revo.PluginShop.infrastructure.application.rest;

import com.revo.PluginShop.MysqlContainer;
import com.revo.PluginShop.infrastructure.application.rest.dto.ProjectRestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;

import static com.revo.PluginShop.MysqlContainer.APPLICATION_JSON_UTF8;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class ProjectControllerTest extends MysqlContainer {

    private static final String PHONE = "000000000";
    private static final String EMAIL = "test@integration.pl";
    private static final String DESCRIPTION = "Integration Test Description";
    private static final String PROJECTS_END_POINT = "/projects";
    private static final String PHONE_JSON_PATH = "$.phone";
    private static final String EMAIL_JSON_PATH = "$.email";
    private static final String DESCRIPTION_JSON_PATH = "$.description";

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void processProjectAsk() throws Exception {
        //given
        var projectRestDto = ProjectRestDto.builder()
                .phone(PHONE)
                .email(EMAIL)
                .description(DESCRIPTION)
                .build();
        var jsonString = objectMapper.writeValueAsString(projectRestDto);
        //when
        //then
        mockMvc.perform(post(URI.create(PROJECTS_END_POINT))
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonString))
                .andExpect(jsonPath(PHONE_JSON_PATH).value(PHONE))
                .andExpect(jsonPath(EMAIL_JSON_PATH).value(EMAIL))
                .andExpect(jsonPath(DESCRIPTION_JSON_PATH).value(DESCRIPTION))
                .andExpect(status().is(201));
    }
}