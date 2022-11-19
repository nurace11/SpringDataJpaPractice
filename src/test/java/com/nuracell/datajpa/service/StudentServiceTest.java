package com.nuracell.datajpa.service;

import com.nuracell.datajpa.entity.Guardian;
import com.nuracell.datajpa.entity.Student;
import com.nuracell.datajpa.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;
    StudentService underTest;

    Long id = 3L;
    String updateName = "Sss";
    Student testStudent;

    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository);

        testStudent = Student.builder()
                .id(id)
                .name("Testing")
                .email("testing@stud.ent")
                .guardian(Guardian.builder().build())
                .build();
    }

    @Test
    void canGetStudents() {
        underTest.getStudents();

        verify(studentRepository).findAll();
    }



    @Test
    void canGetStudentById() {
        Long id = 5L;
        given(studentRepository.findById(id)).willReturn(
                Optional.ofNullable(Student.builder()
                        .id(id)
                        .name("Testing")
                        .email("testing@stud.ent")
                        .guardian(Guardian.builder().build())
                        .build())
        );

        underTest.getStudentById(id);

        verify(studentRepository).findById(id);
    }

    @Test
    void getStudentByIdWillThrow() {
        Long id = 5L;
        given(studentRepository.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.getStudentById(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Student with ID" + id + " not found.");

        verify(studentRepository).findById(id);
    }

    @Test
    void getStudentByIdWillThrowNullPointerException() throws NullPointerException {
        given(studentRepository.findById(anyLong())).willReturn(null);
        assertThatThrownBy(() -> underTest.getStudentById(anyLong())).isInstanceOf(NullPointerException.class);
    }

    @Test
    void getStudentByIdWillThrowRuntimeException() throws Throwable {
        given(studentRepository.findById(any())).willThrow(RuntimeException.class); // can't throw IOException
        assertThatThrownBy(() -> underTest.getStudentById(any())).isInstanceOf(RuntimeException.class);
    }




    @Test
    void canUpdateStudentNameById() {
        given(studentRepository.findById(id)).willReturn(
                Optional.ofNullable(testStudent));

        String beforeUpdatingName = testStudent.getName();
        assertThat(testStudent.getName()).isEqualTo(beforeUpdatingName);

        underTest.updateStudentNameById(id, updateName);

        assertThat(testStudent.getName()).isNotEqualTo(beforeUpdatingName);

        verify(studentRepository).save(testStudent);
    }
}