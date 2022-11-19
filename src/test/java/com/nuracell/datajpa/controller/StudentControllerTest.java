package com.nuracell.datajpa.controller;

import com.nuracell.datajpa.DatajpaApplication;
import com.nuracell.datajpa.repository.StudentRepository;
import com.nuracell.datajpa.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
//@SpringBootTest
@WebMvcTest(StudentController.class)
@ContextConfiguration(classes = {DatajpaApplication.class, StudentRepository.class, StudentService.class})
class StudentControllerTest {
    @Autowired
    private MockMvc mvc;

//    private StudentController underTest;


    @Test
    void hello() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("api/v1/students/hello");
        MvcResult result = mvc.perform(request).andReturn();

        assertEquals("Hello, World", result.getResponse().getContentAsString());
    }

    @Test
    void canGetStudents() {
    }

    @Test
    void canGetStudent() {
    }
}