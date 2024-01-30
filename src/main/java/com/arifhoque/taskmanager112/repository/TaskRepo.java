package com.arifhoque.taskmanager112.repository;

import com.arifhoque.taskmanager112.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
    Task findTaskById(Long id);
}
