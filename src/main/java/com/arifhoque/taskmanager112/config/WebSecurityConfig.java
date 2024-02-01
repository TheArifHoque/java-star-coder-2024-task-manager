package com.arifhoque.taskmanager112.config;

import com.arifhoque.taskmanager112.service.CustomerDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

/**
 * Configuration class for defining security in the application
 *
 * @author Ariful Hoque
 */
@Configuration
public class WebSecurityConfig {

    private final CustomerDetailsService customerDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    public WebSecurityConfig(CustomerDetailsService customerDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customerDetailsService = customerDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    /**
     * Define a bean for the PasswordEncoder
     * For keeping it easy at initial phase NoOpPasswordEncoder is used
     * Used BcryptPasswordEncoder
     *
     * @return the password encoder bean
     */
    @Bean
//    @SuppressWarnings("deprecation")
    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

    /**
     * Defines a bean for the AuthenticationManager
     *
     * @param authenticationConfiguration the AuthenticationConfiguration
     * @return AuthenticationManager bean
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Defined SecurityFilterChain for handling security configurations
     * As I couldn't integrate frontend due to some issue with CORS I kept CSRF to test my code in PostMan
     *
     * @param httpSecurity the HttpSecurity object to configure security filters
     * @return the SecurityFilterChain instance defining the order and behavior for security filters
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requestConfigrer -> requestConfigrer
                        .requestMatchers(antMatcher("/api/login/**")).permitAll()
                        .requestMatchers(antMatcher("/api/register/**")).permitAll()
                        .requestMatchers(antMatcher("/api/tasks/**")).hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .anyRequest().permitAll()
                )
                .userDetailsService(customerDetailsService)
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(sessionConfigurer -> sessionConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        return httpSecurity.build();
    }
}
