package com.arifhoque.taskmanager112.repository;

import com.arifhoque.taskmanager112.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepo extends JpaRepository<Authority, Integer> {
    Authority findByAuthority(String authority);
}
