package com.nuracell.datajpa.repository;

import com.nuracell.datajpa.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User.UserBuilder userBuilder = User.builder();
        userRepository.saveAll(
                List.of(
                        userBuilder.username("ll_//").build(),
                        userBuilder.username("Crook").build(),
                        userBuilder.username("Don").build(),
                        userBuilder.username("Mohito").build()
                )
        );
    }

    @Test
    void findUserByUsername() throws Exception {
        User userFromDB = userRepository.findUserByUsername("Mohito");
        assertThat(userFromDB).isNotNull();
        System.out.println(userFromDB);
    }

    @Test
    void findAllUsers() {
        List<User> allUsers = userRepository.findAll();
        User userToCompare = User.builder().id(4L).username("Mohito").build();

        assertThat(allUsers).asList().contains(userToCompare);
        System.out.println("userRepository.findAll()).asList().contains()" );
        assertThat(allUsers.contains(userToCompare)).isTrue();
        System.out.println("userRepository.findAll().contains()");

        // if there is no equals() and hashcode() methods in object, use this.
        assertThat(allUsers.get(3)).isEqualToComparingFieldByField(userToCompare);
    }
}