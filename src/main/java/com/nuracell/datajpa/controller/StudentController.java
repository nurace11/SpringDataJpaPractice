package com.nuracell.datajpa.controller;

import com.nuracell.datajpa.entity.Student;
import com.nuracell.datajpa.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/students")
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/helloStudent")
    public String helloStudent(@RequestParam(name = "name", defaultValue = "World") String message){
        return studentService.hello(message);
    }

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping(path = "{studentId}")
    public Student getStudent(@PathVariable(value = "studentId") Long id) {
        return studentService.getStudentById(id);
    }

    @PostMapping
    public String saveStudent(@RequestBody Student student) {
        try {
            studentService.saveStudent(student);
            return "Student successfully " + student + " saved";
        } catch (IllegalStateException e) {
            return "Error. " + e.getMessage();
        }
    }

    @PutMapping(path = "usingString/{studentId}")
    public String updateStudentString(@PathVariable(value = "studentId") Long id, @RequestBody String newName) {
        try {
            studentService.updateStudentNameById(id, newName);
            return "Student with ID " + id + " has been updated";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error " + e.getMessage();
        }
    }

    @PutMapping(path = "{studentId}")
    public String updateStudent(@PathVariable(value = "studentId") Long id, @RequestBody Student newStudent) {
        try {
            studentService.updateStudentNameById(id, newStudent.getName());
            return "Student with ID " + id + " has been updated";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error " + e.getMessage();
        }
    }

    @DeleteMapping("{id}")
    public String deleteStudent(@PathVariable(value = "id") Long id) {
        try {
            studentService.deleteStudentById(id);
            return "Student with ID " + id + "has been successfully deleted";
        } catch (Exception e) {
            return "Error. " + e.getMessage();
        }
    }


    // TODO: add POST, PUT, DELETE mappings
}
