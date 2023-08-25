package com.GB.todolist.repository;

import com.GB.todolist.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserModelRepository extends JpaRepository<UserModel, Long> {

    @Query(value = "SELECT * FROM users WHERE username = :username", nativeQuery = true)
    Optional<UserModel> findByUsername(@Param("username") String username);
}
