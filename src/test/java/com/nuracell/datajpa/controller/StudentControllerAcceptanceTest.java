package com.nuracell.datajpa.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerAcceptanceTest{

    private final String RequestMappingURL = "/api/v1/students";
    @Autowired
    private MockMvc mockMvc;

    @Test
    void canHello() throws Exception {
        RequestBuilder request = get(RequestMappingURL + "/helloStudent");

        ResultActions resultActions = mockMvc.perform(request);
        MvcResult result = resultActions.andReturn();

        System.out.println(result
                .getResponse()
                .getContentAsString());

        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Hello World")));
    }

    @Test
    void canHelloWithParameter() throws Exception {
        System.out.println(mockMvc.perform(get("/api/v1/students/helloStudent?name=CZ"))
                .andReturn()
                .getResponse()
                .getContentAsString());
    }

    @Test
    void canGetStudents() throws Exception {
        System.out.println(mockMvc.perform(get("/api/v1/students"))
                .andReturn()
                .getResponse()
                .getContentAsString());
    }

    @Test
    void canGetStudent() throws Exception {
        Long id = 3L;
        RequestBuilder request = get(RequestMappingURL + "/{studentId}", id)
                .contentType(MediaType.APPLICATION_JSON);

//        mockMvc.perform(request)
//            .andExpect(status().isNotFound());

/*        mockMvc.perform(request)
                .andExpect(
                        result -> assertEquals("Student with ID " + id + " not found.",
                                result.getResolvedException().getMessage()));*/
    }
}