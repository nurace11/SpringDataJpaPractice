package com.nuracell.datajpa.repository;

import com.nuracell.datajpa.entity.Guardian;
import com.nuracell.datajpa.entity.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfStudentEmailExists() {
        // given
        String email = "Keek@gmail.com";
        Student student = new Student(
                "Kek",
                email,
                new Guardian("gg",
                        "gg@mail.com",
                        "+1 547 87456")
        );
        underTest.save(student);

        // when
        boolean expected = underTest.selectExistsEmail(email);

        // then
        assertThat(expected).isTrue();
    }

    @Test
    void itShouldCheckIfStudentEmailDoesNotExists() {
        // given
        String email = "Keek@gmail.com";

        // when
        boolean expected = underTest.selectExistsEmail(email);

        // then
        assertThat(expected).isFalse();
    }
}

//sealed interface Numbers permits Aboba {}
//sealed interface Aboba extends Numbers{}
//final class Eager implements Aboba{}