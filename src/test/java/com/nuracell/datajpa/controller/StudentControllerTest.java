package com.nuracell.datajpa.controller;

import com.nuracell.datajpa.entity.Guardian;
import com.nuracell.datajpa.entity.Student;
import com.nuracell.datajpa.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class StudentControllerTest {

    StudentController underTest;
    StudentService studentServiceMock;

    @BeforeEach
    void setUp() {
        studentServiceMock = Mockito.mock(StudentService.class);
        underTest = new StudentController(studentServiceMock);
    }

    @Test
    void canHelloWithParam() {
        String result = underTest.helloStudent("George");
        System.out.println(result);
        assertThat(result).isEqualTo("Hello George");
    }

    @Test
    void canGetStudents() {
        Student.StudentBuilder studentBuilder = Student.builder();

        given(studentServiceMock.getStudents()).willReturn(
                List.of(studentBuilder.name("Bulb").email("Bg").guardian(Guardian.builder().build()).build(),
                        studentBuilder.name("Lisa").email("Lg").guardian(Guardian.builder().build()).build(),
                        studentBuilder.name("Tulp").email("Tg").guardian(Guardian.builder().build()).build())
        );

        List<Student> students = underTest.getStudents();
        System.out.println(students);

        verify(studentServiceMock).getStudents();
    }

    @Test
    void canGetStudent() {
        Student testStudent = Student.builder()
                .name("Bulb")
                .email("Bg")
                .guardian(Guardian.builder().build()).build();

        given(studentServiceMock.getStudentById(any())).willReturn( testStudent );

        assertThat(underTest.getStudent(any())).isEqualTo(testStudent);

        verify(studentServiceMock).getStudentById(any());
    }
}