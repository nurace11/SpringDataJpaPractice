package com.nuracell.datajpa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {

    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )
    private Long courseId;
    private String title;
    private Integer credit;

    @OneToOne(
            mappedBy = "course" // field name in the CourseMaterial class
    )
    private CourseMaterial courseMaterial;

    // Define relationship in ManyToOne object, it is easier to read and more understandable than defining @OneToMany relation
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn (
            name = "teacher_id",
            referencedColumnName = "teacherId"
    )
    private Teacher teacher;

    @ManyToMany(
            cascade = CascadeType.ALL
    )
    @JoinTable( // new table contains two columns
            name = "student_course_map",
            joinColumns = @JoinColumn(
                    name = "course_id",
                    referencedColumnName = "courseId" // field name in this (Course) class
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "student_id",
                    referencedColumnName = "id" // field name in Student class
            )
    )
    private List<Student> students;

    public void addStudents(Student student) {
        if(students == null ) {
            students = new ArrayList<>();
        }

        students.add(student);
    }
}
