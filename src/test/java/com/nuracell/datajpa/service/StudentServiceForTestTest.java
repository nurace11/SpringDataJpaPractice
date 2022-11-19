package com.nuracell.datajpa.service;

import com.nuracell.datajpa.entity.Guardian;
import com.nuracell.datajpa.entity.Student;
import com.nuracell.datajpa.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceForTestTest {

    @Mock
    private StudentRepository studentRepository;
    private StudentServiceForTest underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentServiceForTest(studentRepository);
    }
    @Test
    void canGetAllStudents() {
        // when
        underTest.getAllStudents();

        // then
        verify(studentRepository).findAll();
    }

    @Test
    void canAddStudent() {
        // given
        Student student = Student.builder()
                .name("Amougs")
                .email("qwert@gmail.com")
                .guardian(Guardian.builder().email("gg@qwe.c").mobile("+1 717171").name("Magnus").build())
                .build();

        // when
        underTest.addStudent(student);

        // then
        ArgumentCaptor<Student> argumentCaptor = ArgumentCaptor.forClass(Student.class);

        verify(studentRepository).save(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()).isEqualTo(student);
    }
    @Test
    void willThrowWhenEmailIsTaken() {
        // given
        Student student = Student.builder()
                .name("Amougs")
                .email("qwert@gmail.com")
                .guardian(Guardian.builder().email("gg@qwe.c").mobile("+1 717171").name("Magnus").build())
                .build();

        given(studentRepository.selectExistsEmail(anyString()/*student.getEmail()*/))
                .willReturn(true);

        // when
        // then
        assertThatThrownBy(() -> underTest.addStudent(student))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Email " + student.getEmail() + " taken");

        // if exception is called, studentRepository will not save a student
        verify(studentRepository, never()).save(any());
        ;
    }


    @Test
    @Disabled
    void deleteStudent() {
    }
}