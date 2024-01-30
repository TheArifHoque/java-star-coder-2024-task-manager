package com.arifhoque.taskmanager112.repository;

import com.arifhoque.taskmanager112.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsernameIgnoreCase(String username);

    User findByEmailIgnoreCase(String email);
}
