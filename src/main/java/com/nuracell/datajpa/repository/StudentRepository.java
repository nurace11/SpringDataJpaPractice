package com.nuracell.datajpa.repository;

import com.nuracell.datajpa.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAllByName(String name);

    Student findByName(String name); // can return more than 1 student, because name is not unique. TODO: change return type to List

    Student findStudentByGuardianName(String guardianName);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM tbl_student WHERE name LIKE(?1)"
    )
    List<Student> getStudentsWhereNameLike(String nameLike);

    //JPQL
    @Query(value = "SELECT s FROM Student s WHERE s.name LIKE(?1)") // LIKE(%u%) finds more than 1 student. Return type must be List
    List<Student> getStudentsWhereNameLikeJPQL(String nameLike);

    // Named param
    @Query(value = "SELECT s FROM Student s WHERE s.email = :paramEmail")
    Student getStudentByEmail(@Param("paramEmail") String email);
}
