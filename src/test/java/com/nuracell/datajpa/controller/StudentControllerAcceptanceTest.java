package com.nuracell.datajpa.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuracell.datajpa.entity.Guardian;
import com.nuracell.datajpa.entity.Student;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import java.io.BufferedOutputStream;
import java.io.IOException;

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
    static ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void beforeAll() throws IOException {
        objectMapper = new ObjectMapper();
        testStudent = Student.builder()
                .id(1L)
                .name("Ind")
                .email("qw@re.to")
                .guardian(Guardian.builder()
                        .name("Jimmy")
                        .email("indguardian@wer.io")
                        .build()).build();
    }

    @Test
    @Order(Integer.MIN_VALUE)
    void canAddStudent() throws Exception {
        RequestBuilder requestBuilder = post(RequestMappingURL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writer().writeValueAsString(testStudent));
        mockMvc.perform(requestBuilder);
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
    @Order(3)
    void canGetStudents() throws Exception {
        System.out.println(mockMvc.perform(get("/api/v1/students"))
                .andReturn()
                .getResponse()
                .getContentAsString());
    }

    @Test
    @Order(5)
    @Disabled
    void canUpdateStudentWithString() throws Exception {
        RequestBuilder requestBuilder = put(RequestMappingURL + "/usingString/{studentId}", 1)
                .characterEncoding("UTF-8")
                .accept(MediaType.ALL)
                .contentType(MediaType.ALL)
                .content("board");

        Student student = getStudentFromResponseBody(mockMvc.perform(get(RequestMappingURL + "/{studentId}", 1))
                .andReturn().getResponse().getContentAsString());
        assertThat(student).isEqualTo(testStudent);

        assertThat(mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString().isEmpty()).isFalse();
    }

    @Test
    @Order(6)
    void canUpdateStudent() throws Exception {
        // get student
        RequestBuilder getStudentRequest = get(RequestMappingURL + "/{studentId}", 1);
        Student newStudent = getStudentFromResponseBody(
                mockMvc.perform(getStudentRequest).andReturn().getResponse().getContentAsString());

        assertThat(newStudent).isEqualTo(testStudent);

        // update student
        newStudent.setName("Amongus");

        RequestBuilder requestBuilder = put(RequestMappingURL + "/{studentId}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writer().writeValueAsString(newStudent));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Student with ID 1 has been updated")));

        // assert updated student
        newStudent = getStudentFromResponseBody(
                mockMvc.perform(getStudentRequest).andReturn().getResponse().getContentAsString());

        assertThat(newStudent).isNotEqualTo(testStudent);
    }

    @Test
    @Order(7)
    void canDeleteStudent() throws Exception {
        mockMvc.perform(delete(RequestMappingURL + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Student with ID 1 has been successfully deleted")));
    }

    @Test
    @Order(8)
    void canDeleteStudentError() throws Exception {
        System.out.println(mockMvc.perform(delete(RequestMappingURL + "/2"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Error. No value present"))));
    }

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

    public Student getStudentFromResponseBody(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Student.class);
    }
}

                        /*"    {\n" +
                        "        \"id\": 1,\n" +
                        "        \"name\": \"ISO\",\n" +
                        "        \"email\": \"is1o@gmail.com.uk\",\n" +
                        "        \"guardian\": {\n" +
                        "            \"name\": \"Carlos\",\n" +
                        "            \"email\": \"ggcarlos@gmail.com\",\n" +
                        "            \"mobile\": null\n" +
                        "        }\n" +
                        "    }"*/