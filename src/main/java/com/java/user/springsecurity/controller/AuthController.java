package com.java.user.springsecurity.controller;

import com.java.user.springsecurity.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SecurityService securityService;

    @GetMapping("/public")
    public String publicPage() {
        return "hello world";
    }

    @GetMapping("/role_user")
    public String roleUser() {
        return "ROLE_USER";
    }

    @GetMapping("/role_admin")
    public String roleAdmin() {
        return "ROLE_ADMIN";
    }

    @GetMapping("/pre-authorized")
    public String getHelloAuthorized(){
        return securityService.sayHelloService();
    }

    @GetMapping("/post-authorized")
    public String getHelloPostAuthorized(@RequestParam Integer id){
        return securityService.sayHelloSecure(id);
    }
}
