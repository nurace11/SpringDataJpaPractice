package com.nuracell.datajpa.repository;

import com.nuracell.datajpa.entity.Course;
import com.nuracell.datajpa.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository repository;

    @Test
    public void printCourses() {
        List<Course> courseList = repository.findAll();
        System.out.println(courseList);
    }

    @Test
    public void saveCourseWIthTeacher() {
        Teacher teacher = Teacher.builder()
                .firstName("L01")
                .lastName("Bo")
                .build();

        Course course = Course
                .builder()
                .title("React")
                .credit(5)
                .teacher(teacher)
                .build();

        repository.save(course);
    }
}