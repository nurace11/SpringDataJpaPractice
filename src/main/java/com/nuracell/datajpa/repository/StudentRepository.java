package com.nuracell.datajpa.repository;

import com.nuracell.datajpa.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "UPDATE tbl_student SET name = ?1 WHERE email_address = ?2"
    )
    int updateStudentNameByEmail(String name, String email);

    @Query(
            "SELECT CASE WHEN COUNT(s) > 0 THEN " +
                    "TRUE ELSE FALSE END " +
                    "FROM Student s " +
                    "WHERE s.email = ?1"
    )
    Boolean selectExistsEmail(String email);
}
