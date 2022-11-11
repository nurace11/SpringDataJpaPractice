package com.nuracell.datajpa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
}
