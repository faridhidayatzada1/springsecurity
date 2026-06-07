package com.java.user.springsecurity.service;

import com.java.user.springsecurity.config.JwtTokenConfigProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtTokenConfigProperties tokenConfigProperties;

    private SecretKey key;

    @PostConstruct
    public void init() {
        if (StringUtils.isBlank(tokenConfigProperties.getJwtProperties().getSecret())) {
            throw new RuntimeException("Token config not initialized");
        }
        byte[] keyBytes = tokenConfigProperties.getJwtProperties().getSecret().getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String parseToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .clockSkewSeconds(600)
                .build()
                .parseSignedClaims(token)
                .getPayload().toString();

    }
}
