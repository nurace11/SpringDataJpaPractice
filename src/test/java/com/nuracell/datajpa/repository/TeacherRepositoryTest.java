package com.nuracell.datajpa.repository;

import com.nuracell.datajpa.entity.Course;
import com.nuracell.datajpa.entity.Teacher;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository repository;

    @Test
    public void saveTeacher() {
        Course courseMath = Course.builder()
                .title("Math I")
                .credit(5)
                .build();

        Course coursePsychology = Course.builder()
                .title("Psychology")
                .credit(5)
                .build();

        Course courseEconomics = Course.builder()
                .title("Economics")
                .credit(5)
                .build();


        Teacher teacher = Teacher.builder()
                .firstName("Kek")
                .lastName("Teacher")
//                .courses(List.of(courseMath, coursePsychology, courseEconomics))
                .build();

        repository.save(teacher);
    }
}