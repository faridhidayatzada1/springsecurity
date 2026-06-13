package com.java.user.springsecurity.controller;

import com.java.user.springsecurity.model.TokenRequest;
import com.java.user.springsecurity.service.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jwt")
@RequiredArgsConstructor
public class JwtController {

    private final JwtService jwtService;

    @PostMapping("/token-parse")
    public String getParseJwtToken(@RequestBody TokenRequest token) {
        return jwtService.parseToken(token.getToken());
    }

    @GetMapping("/issue-token")
    public String getIssueJwtToken(){
        return jwtService.issueToken();
    }
}
