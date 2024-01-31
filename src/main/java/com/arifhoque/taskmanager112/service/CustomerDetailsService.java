package com.arifhoque.taskmanager112.service;

import com.arifhoque.taskmanager112.model.Authority;
import com.arifhoque.taskmanager112.model.User;
import com.arifhoque.taskmanager112.repository.UserRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Service class for customer details.
 * @author Ariful Hoque
 */
@Service
public class CustomerDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    public CustomerDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * This is a implemented method derived from UserDetailsService of spring core.
     * it is used to convert a user to userdetails. this object encapsulates the userdetails
     * transactional annotation is used to perform database operation and it'll rollback on failed operation
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsernameIgnoreCase(username);

        if (user == null) {
            throw new UsernameNotFoundException("No user found with this username: " + username);
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Authority authority : user.getAuthorities()) {
            authorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
