package com.arifhoque.taskmanager112.service;

import com.arifhoque.taskmanager112.model.Authority;
import com.arifhoque.taskmanager112.model.User;
import com.arifhoque.taskmanager112.repository.AuthorityRepo;
import com.arifhoque.taskmanager112.repository.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * UserService class to save  user with designated role/authority
 * and get user by their username or email
 *
 * @author Ariful Hoque
 */
@Service
public class UserService {
    private final UserRepo userRepo;
    private final AuthorityRepo authorityRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, AuthorityRepo authorityRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.authorityRepo = authorityRepo;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Method to find user by their username
     *
     * @param username
     * @return the desired user
     */
    public User findUserByUsername(String username) {
        return userRepo.findByUsernameIgnoreCase(username);
    }

    /**
     * Method to find user by their email
     *
     * @param email
     * @return the desired user
     */
    public User findUserByEmail(String email) {
        return userRepo.findByEmailIgnoreCase(email);
    }

    /**
     * Method to save user with user role
     *
     * @param user
     */
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Authority authority = authorityRepo.findByAuthority("ROLE_USER");
        user.setAuthorities(Set.of(authority));
        userRepo.save(user);
    }

    /**
     * Method to save user with admin role
     *
     * @param user
     */
    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Authority authority = authorityRepo.findByAuthority("ROLE_ADMIN");
        user.setAuthorities(Set.of(authority));
        userRepo.save(user);
    }
}
