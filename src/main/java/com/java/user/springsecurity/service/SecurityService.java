package com.java.user.springsecurity.service;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @PreAuthorize("hasAuthorized('permission:read') && hasRole('ADMIN')")
    public String sayHelloService(){
        return "Hello Admin";
    }

    @PostAuthorize("returnObject == authentication.name && hasRole('ADMIN')")
    public String sayHelloSecure(Integer id){
        if (id == 1){
            return "user";
        }
        else if (id == 2){
            return "admin";
        } else if (id == 3) {
            return "atilla";
        }
        return "User not found";
    }
}
