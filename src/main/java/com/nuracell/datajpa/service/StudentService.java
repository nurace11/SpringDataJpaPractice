package com.nuracell.datajpa.service;

import com.nuracell.datajpa.entity.Student;
import com.nuracell.datajpa.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void saveStudent(Student student) {
        Optional<Student> studentOptional = repository.findById(student.getId());
        if(studentOptional.isPresent()) {
            throw new IllegalArgumentException("Student with ID " + student.getId() + " already exists");
        }

        studentOptional = repository.findStudentByEmail(student.getEmail());
        if(studentOptional.isPresent()){
            throw new IllegalStateException("Email taken");
        }

        repository.save(student);
    }

    public void deleteStudentById(Long id) {
        repository.delete(findStudentById(id));
    }

    public Student findStudentById(Long id) {
       return repository.findById(id).orElseThrow();
    }

    public String hello(String message) {
        return String.format("Hello %s", message);
    }
}
