package com.nuracell.datajpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuracell.datajpa.entity.Guardian;
import com.nuracell.datajpa.entity.Student;
import com.nuracell.datajpa.service.StudentService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StudentController.class)
class StudentControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudentService studentService;

    @Test
    void canHello() throws Exception {
        given(studentService.hello(any())).willReturn(
                "Hello World"
        );

        RequestBuilder request = get("/api/v1/students/helloStudent");
        MvcResult result = mvc.perform(request).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void canHelloWithParameter() throws Exception {
        String param = "Josh";
        given(studentService.hello(param)).willReturn("Hello Josh");

        String contentAsString = mvc.perform(get("/api/v1/students/helloStudent?name=" + param))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(contentAsString).isEqualTo("Hello Josh");

        verify(studentService).hello(param);
    }

    @Test
    void canGetStudents() throws Exception {
        Student.StudentBuilder studentBuilder = Student.builder();
        List<Student> testStudentList = List.of
                (studentBuilder.name("Bulb").email("Bg").guardian(Guardian.builder().build()).build(),
                studentBuilder.name("Lisa").email("Lg").guardian(Guardian.builder().build()).build(),
                studentBuilder.name("Tulp").email("Tg").guardian(Guardian.builder().build()).build());

        given(studentService.getStudents()).willReturn(testStudentList);

        String contentAsString = mvc.perform(get("/api/v1/students"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        System.out.println(contentAsString);

        verify(studentService).getStudents();
    }

    @Test
    void canGetStudent() throws Exception {
        Student testStudent = Student.builder()
                .name("Bulb")
                .email("Bg")
                .guardian(Guardian.builder().build())
                .build();

        given(studentService.getStudentById(anyLong())).willReturn(testStudent);

        String contentAsString = mvc.perform(get("/api/v1/students/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();


        ObjectMapper objectMapper = new ObjectMapper(); // Jackson
        Student studentJsonFromResponseBody = objectMapper.readValue(contentAsString, Student.class);

        assertThat(studentJsonFromResponseBody).isEqualTo(testStudent);

        verify(studentService).getStudentById(anyLong());
    }
}