package com.java.user.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/public")
    public String publicPage() {
        return "hello world";
    }

    @GetMapping("/role_admin")
    public String roleAdmin() {
        return "ROLE_ADMIN";
    }
}
