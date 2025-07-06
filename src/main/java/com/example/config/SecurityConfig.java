package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for WebSocket endpoints (required for WebSocket to work)
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/ws/**")
                )

                // Configure authorization - allow all requests for this demo
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/ws/**", "/chat", "/", "/css/**", "/js/**", "/images/**").permitAll()
                        .anyRequest().permitAll() // Allow all for demo purposes
                )

                // Add basic security headers
                .headers(headers -> headers
                        .frameOptions().deny()
                        .contentTypeOptions().and()
                );

        return http.build();
    }
}

// If you don't want any security at all, you can use this instead:
/*
@Configuration
public class NoSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(authz -> authz
                .anyRequest().permitAll()
            );
        return http.build();
    }
}
*/