package com.nuracell.datajpa.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuracell.datajpa.entity.Student;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentControllerAcceptanceTest{

    private final String RequestMappingURL = "/api/v1/students";
    static private Student testStudent;
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
    @Order(3)
    void canGetStudents() throws Exception {
        System.out.println(mockMvc.perform(get("/api/v1/students"))
                .andReturn()
                .getResponse()
                .getContentAsString());
    }

    @Test
    @Order(2)
    void canGetStudentThrows() throws Exception {
        Long id = 1000L;
        RequestBuilder request = get(RequestMappingURL + "/{studentId}", id)
                .contentType(MediaType.APPLICATION_JSON);

        assertThatThrownBy(() -> mockMvc.perform(request));

//        mockMvc.perform(request)
//            .andExpect(status().isNotFound());

/*        mockMvc.perform(request)
                .andExpect(
                        result -> assertEquals("Student with ID " + id + " not found.",
                                result.getResolvedException().getMessage()));*/
    }

    @Test
    @Order(1)
    void canGetStudent() throws Exception {
        RequestBuilder request = get(RequestMappingURL + "/{studentId}", 1)
                .contentType(MediaType.APPLICATION_JSON);

        String jsonStudent = (mockMvc.perform(request)
                .andReturn()
                .getResponse()
                .getContentAsString());

        ObjectMapper objectMapper = new ObjectMapper();
        testStudent = objectMapper.readValue(jsonStudent, Student.class);
    }

    @Test
    @Order(Integer.MIN_VALUE)
    void canAddStudent() throws Exception {
        RequestBuilder requestBuilder = post(RequestMappingURL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("    {\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"ISO\",\n" +
                "        \"email\": \"is1o@gmail.com.uk\",\n" +
                "        \"guardian\": {\n" +
                "            \"name\": \"Carlos\",\n" +
                "            \"email\": \"ggcarlos@gmail.com\",\n" +
                "            \"mobile\": null\n" +
                "        }\n" +
                "    }");
        mockMvc.perform(requestBuilder);
    }

    @Test
    @Order(5)
    void canUpdateStudentWithString() throws Exception {
        RequestBuilder requestBuilder = put(RequestMappingURL + "/usingString/{studentId}", 1)
                .characterEncoding("UTF-8")
                .contentType("board")
                .content("board");

        Student student = getStudentFromResponseBody(mockMvc.perform(get(RequestMappingURL + "/{studentId}", 1))
                .andReturn().getResponse().getContentAsString());
        assertThat(student).isEqualTo(testStudent);

        ResultActions perform = mockMvc.perform(requestBuilder);
        MvcResult result = perform.andReturn();
        System.out.println("result.getRequest().getContentAsString() = " + result.getRequest().getContentAsString());
        System.out.println("result.getRequest().getContextPath() = " + result.getRequest().getContextPath());
        System.out.println("result.getRequest().getPathInfo() = " + result.getRequest().getPathInfo());
        System.out.println("result.getRequest().getServletPath() = " + result.getRequest().getServletPath());
        System.out.println("mockMvc response: " +
                mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString());

/*        student = getStudentFromResponseBody(mockMvc.perform(get(RequestMappingURL + "/{studentId}", 1))
                .andReturn().getResponse().getContentAsString());
        assertThat(student).isNotEqualTo(testStudent);*/

//        assertThat(updatingStudent).isEqualTo(testStudent);
    }

    static ObjectMapper objectMapper = new ObjectMapper();
    public Student getStudentFromResponseBody(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Student.class);
    }
}