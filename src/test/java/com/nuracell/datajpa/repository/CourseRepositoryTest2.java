package com.nuracell.datajpa.repository;

import com.nuracell.datajpa.entity.Course;
import com.nuracell.datajpa.entity.Student;
import com.nuracell.datajpa.entity.Teacher;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CourseRepositoryTest2 {
    @Autowired
    CourseRepository courseRepository;

    @Test
    public void saveCourseWithStudentAndTeacher() {
        Teacher teacher = Teacher.builder()
                .firstName("Margareth")
                .lastName("Packard")
                .build();

        Student student = Student.builder()
                .name("Amogus Ioe")
                .email("quwehr@yahoo.uk")
                .build();

        Course course = Course.builder()
                .title("AI")
                .credit(12)
                .teacher(teacher)
                .build();

        course.addStudents(student);
        courseRepository.save(course);
    }
}
