package com.nuracell.datajpa.repository;

import com.nuracell.datajpa.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAllByName(String name);

    Student findByName(String name);

    Student findStudentByGuardianName(String guardianName);
}
