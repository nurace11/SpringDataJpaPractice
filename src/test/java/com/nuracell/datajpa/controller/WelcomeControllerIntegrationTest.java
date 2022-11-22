package com.nuracell.datajpa.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class WelcomeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WelcomeService welcomeService;
    @MockBean
    private StudentController studentService; // getting error without this

    @Test
    void shouldGetDefaultWelcomeMessage() throws Exception {
        given(welcomeService
                .getWelcomeMessage("Unknown"))
                .willReturn("Welcome Unknown");

        mockMvc.perform(get("/welcome"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Welcome Unknown")));

        verify(welcomeService).getWelcomeMessage("Unknown");
    }

    @Test
    void shouldGetCustomWelcomeMessage() throws Exception {
        given(welcomeService
                .getWelcomeMessage("Hewlett"))
                .willReturn("Welcome Hewlett");

        mockMvc.perform(get("/welcome?message=Hewlett"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Welcome Hewlett")));

        verify(welcomeService).getWelcomeMessage("Hewlett");
    }
}