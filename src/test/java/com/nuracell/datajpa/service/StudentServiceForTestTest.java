package com.nuracell.datajpa.service;

import com.nuracell.datajpa.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceForTestTest {

    @Mock
    private StudentRepository studentRepository;
//    private AutoCloseable autoCloseable;
    private StudentServiceForTest underTest;

    @BeforeEach
    void setUp() {
//        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new StudentServiceForTest(studentRepository);
    }

/*    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }*/

    @Test
    void canGetAllStudents() {
        // when
        underTest.getAllStudents();

        // then
        verify(studentRepository).findAll();
    }

    @Test
    @Disabled
    void addStudent() {
    }

    @Test
    @Disabled
    void deleteStudent() {
    }
}