package com.nuracell.datajpa.entity;

import com.nuracell.datajpa.repository.StudentRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentTest {
    @Autowired
    StudentRepository studentRepository;

    @Test
    @Order(1)
    public void createStudent() {
        Student student = Student.builder()
                .email("lolo@gmail.com")
                .name("abobus")
                .build();

        studentRepository.save(student);
    }

    @Test
    @Order(2)
    public void createStudentWithGuardian() {
        Guardian guardian = Guardian.builder()
                .email("ggcarlos@gmail.com")
                .mobile("+7 777 777 7777")
                .name("Carlos")
                .build();

        Student student = Student.builder()
                .email("lolo123@gmail.com.uk")
                .name("guraded")
                .guardian(guardian)
                .build();

        studentRepository.save(student);
    }

    @Test
    public void printStudentByName() {
        System.out.println(studentRepository.findByName("guraded"));
    }

    @Test
    public void printStudentsByName() {
        System.out.println(studentRepository.findAllByName("abobus"));
    }

    @Test
    public void printStudentByGuardianName() {
        System.out.println(studentRepository.findStudentByGuardianName("Carlos"));
    }



}