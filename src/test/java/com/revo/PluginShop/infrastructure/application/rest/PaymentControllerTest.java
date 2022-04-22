package com.revo.PluginShop.infrastructure.application.rest;

import com.revo.PluginShop.MysqlContainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.net.URI;
import java.sql.Connection;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class PaymentControllerTest extends MysqlContainer {

    private static final String PAYMENTS_URL = "/payments";
    private static final String OPERATION_CURRENCY = "PLN";
    private static final String OPERATION_TYPE = "payment";
    private static final String OPERATION_STATUS = "completed";
    private static final String ID = "775355";
    private static final String OPERATION_NUMBER = "0";
    private static final String OPERATION_AMOUNT = "1.00";
    private static final String DESCRIPTION = "1";
    private static final String EMAIL = "payment@email.pl";
    private static final String WRONG_ID = "0";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldProcessPayment() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(post(URI.create(PAYMENTS_URL))
                .param("id", ID)
                .param("operation_number", OPERATION_NUMBER)
                .param("operation_type", OPERATION_TYPE)
                .param("operation_status", OPERATION_STATUS)
                .param("operation_amount", OPERATION_AMOUNT)
                .param("operation_currency", OPERATION_CURRENCY)
                .param("description", DESCRIPTION)
                .param("email", EMAIL)
        ).andExpect(status().is(200));
    }

    @Test
    void shouldThrowWhileProcessPayment() throws Exception{
        //given
        //when
        //then
        mockMvc.perform(post(URI.create(PAYMENTS_URL))
                .param("id", WRONG_ID)
                .param("operation_number", OPERATION_NUMBER)
                .param("operation_type", OPERATION_TYPE)
                .param("operation_status", OPERATION_STATUS)
                .param("operation_amount", OPERATION_AMOUNT)
                .param("operation_currency", OPERATION_CURRENCY)
                .param("description", DESCRIPTION)
                .param("email", EMAIL)
        ).andExpect(status().is(400));
    }
}