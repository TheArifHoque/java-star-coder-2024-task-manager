package com.arifhoque.taskmanager112.service;

import com.arifhoque.taskmanager112.model.Task;
import com.arifhoque.taskmanager112.repository.TaskRepo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for task model. Here CRUD operation is introduced.
 * @author Ariful Hoque
 */
@Service
public class TaskService {
    private final TaskRepo taskRepo;

    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    /**
     * This method is to get all the available class
     * @return a list of tasks
     */
    public List<Task> getAllTask() {
        return taskRepo.findAll();
    }

    /**
     * This method takes a task as a parameter then saves it in repository
     * @param task
     */
    public void saveTask(Task task) {
        taskRepo.save(task);
    }

    /**
     * This method is for updating an existing task. User can edit anything other than taskId
     * @param task
     */
    public void updateTask(Task task) {
        Task existingTask = taskRepo.findTaskById(task.getId());
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());
        taskRepo.save(existingTask);
    }

    /**
     * This method is for deleting task
     * @param task
     */
    public void deleteTask(Task task) {
        Task existingTask = taskRepo.findTaskById(task.getId());
        taskRepo.delete(existingTask);
    }
}
