package com.java.user.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        String generatedPassword = "{noop}123";
        return new InMemoryUserDetailsManager(User.withUsername("user")
                .password(generatedPassword)
                .roles("USER").build()); //bu role_user 37-ci line görə access verir.
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. URL icazələrinin tənzimlənməsi
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/public").permitAll() // Hamıya açıq səhifələr, auth silsək 403 verəcək.
                        .requestMatchers("/auth/role_user/**").hasRole("USER") // Yalnız adminlər üçün
                        .requestMatchers("/auth/role_admin/**").hasRole("ADMIN") // Buna permission olmayacaq, çünki inMemory-də admin yoxdur.
                        .anyRequest().authenticated() // Qalan bütün səhifələr üçün giriş mütləqdir
                )
                .formLogin(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(); // Şifrələrin təhlükəsiz saxlanması üçün
//    }
}

