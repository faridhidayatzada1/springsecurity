package com.java.user.springsecurity.controller;

import com.java.user.springsecurity.model.TokenRequest;
import com.java.user.springsecurity.service.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwt")
@RequiredArgsConstructor
public class JwtController {

    private final JwtService jwtService;

    @PostMapping("/token-parse")
    public String getParseJwtToken(@RequestBody TokenRequest token) {
        return jwtService.parseToken(token.getToken());
    }
}
