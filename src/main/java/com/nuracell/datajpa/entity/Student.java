package com.nuracell.datajpa.entity;

import lombok.*;


import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
@Table(
        name = "tbl_student",
        uniqueConstraints = @UniqueConstraint(
                name = "emailid_unique",
                columnNames = "email_address"
        )
)

public class Student {
    @Id
    @SequenceGenerator(
            name = "student_generator",
            sequenceName = "student_generator",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_generator"
    )
    private Long id;
    @NonNull
    private String name;

    @NonNull
    @Column(
            name = "email_address",
            nullable = false
    )
    private String email;

    @NonNull
    @Embedded
    Guardian guardian;
}
