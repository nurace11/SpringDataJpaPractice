package com.nuracell.datajpa.entity;

import com.nuracell.datajpa.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StudentTest2 {
    @Autowired
    StudentService studentService;

    @Test
    public void setStudentNameById() {
        // works without @Transactional annotation
        studentService.updateStudentNameById(1L, "CarTop");
        studentService.updateStudentNameById(1L, "abobus");
    }
}
