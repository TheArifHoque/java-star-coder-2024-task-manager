package com.arifhoque.taskmanager112.controller;

import com.arifhoque.taskmanager112.model.JwtResponse;
import com.arifhoque.taskmanager112.model.Task;
import com.arifhoque.taskmanager112.model.User;
import com.arifhoque.taskmanager112.service.TaskService;
import com.arifhoque.taskmanager112.service.UserService;
import com.arifhoque.taskmanager112.util.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TaskController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final TaskService taskService;

    public TaskController(AuthenticationManager authenticationManager, JwtUtils jwtUtils,
                          UserService userService, TaskService taskService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.taskService = taskService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword()));
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>("Incorrect credentials!", HttpStatus.BAD_REQUEST);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);
        JwtResponse jwtResponse = JwtResponse.builder()
                .type("Bearer")
                .username(user.getUsername())
                .token(jwt)
                .build();

        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        User existEmail = userService.findUserByEmail(user.getEmail());
        User existUsername = userService.findUserByUsername(user.getUsername());

        if (existEmail != null && existUsername != null) {
            return new ResponseEntity<>(Map.of("message", "User already exists!"), HttpStatus.CONFLICT);
        }
        userService.saveUser(user);
        return new ResponseEntity<>(Map.of("message", "Registration successful"), HttpStatus.OK);
    }

    @PostMapping("/tasks")
    public ResponseEntity<?> createTasks(@RequestBody Task task) {
        try {
            taskService.saveTask(task);
        } catch (Exception ex) {
            return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(Map.of("message", "New task added"), HttpStatus.OK);
    }

    @PostMapping("/tasks/{taskId}")
    public ResponseEntity<?> updateTasks(@RequestBody Task task, @PathVariable Long taskId) {
        try {
            taskService.updateTask(task, taskId);
        } catch (Exception ex) {
            return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(Map.of("message", "New task added"), HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        try {
            taskService.deleteTask(taskId);
        } catch (Exception ex) {
            return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(Map.of("message", "Task deleted"), HttpStatus.OK);
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getAllTask() {
        List<Task> taskList = taskService.getAllTask();
        if (taskList.isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "No task found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(Map.of("taskList", taskList), HttpStatus.OK);
    }
}
