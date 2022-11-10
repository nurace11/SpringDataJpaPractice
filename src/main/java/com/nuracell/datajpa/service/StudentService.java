package com.nuracell.datajpa.service;

import com.nuracell.datajpa.entity.Student;
import com.nuracell.datajpa.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getStudents() {
        return repository.findAll();
    }


    public Student getStudentById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> {
                    throw new IllegalArgumentException("Student with ID" + id + " not found.");
                }
        );
    }

    public void updateStudentNameById(Long id, String name) {
        Student student = repository.findById(id).orElseThrow();
        student.setName(name);
        repository.save(student);
    }

}
