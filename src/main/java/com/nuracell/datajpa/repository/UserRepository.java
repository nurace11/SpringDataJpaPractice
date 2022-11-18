package com.nuracell.datajpa.repository;

import com.nuracell.datajpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String name) throws Exception;

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM tbl_user"
    )
    List<User> findUsers();
}
