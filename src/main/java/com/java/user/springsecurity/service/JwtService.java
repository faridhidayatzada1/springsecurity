package com.java.user.springsecurity.service;

import com.java.user.springsecurity.config.JwtTokenConfigProperties;
import com.java.user.springsecurity.properties.ApplicationProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtTokenConfigProperties tokenConfigProperties;
    private final ApplicationProperties applicationProperties;

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

    private static final long CLOCK_SKEW_SECONDS = 10;
    private static final long EXPIRATION_SECONDS = 3600;

    public String issueToken(){

        ApplicationProperties.Client.Akb.Credentials credentials =
                applicationProperties.getClient().getAkb().getCredentials();

        Map<String, Object> claims = Map.of(
                "iss",credentials.getIss(),
                "aud",credentials.getAud()

        );

        long nowInstant = Instant.now().getEpochSecond();
        long iat = nowInstant - CLOCK_SKEW_SECONDS;
        long exp = nowInstant + EXPIRATION_SECONDS;

        return Jwts
                .builder()
                .header()
                .type("JWT")
                .and()
                .claims(claims)
                .issuedAt(Date.from(Instant.ofEpochSecond(iat)))
                .expiration(Date.from(Instant.ofEpochSecond(exp)))
                .signWith(key, Jwts.SIG.HS256)
                .compact();


    }
}
