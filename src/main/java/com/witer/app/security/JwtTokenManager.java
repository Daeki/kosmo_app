package com.witer.app.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

//token을 생성, 관리 하는 클래스

@Component
public class JwtTokenManager {

    // 1. access token 유효시간
    @Value("${jwt.accessValidTime}")
    private Long accessValidTime;

    // 2. refresh token 유효시간
    @Value("${jwt.refreshValidTime}")
    private Long refreshValidTime;

    @Value("${jwt.issur}")
    private String issur;

    @Value("${jwt.secretKey}")
    private String secretKey;

    private SecretKey key;

    @PostConstruct
    public void init() {

        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // access token을 생성하는 메서드
    public String createAccessToken(Authentication authentication) {
        return this.createToken(authentication, accessValidTime);
    }

    // refresh token을 생성하는 메서드
    public String createRefreshToken(Authentication authentication) {
        return this.createToken(authentication, refreshValidTime);
    }

    // 토큰 생성하는 메서드
    private String createToken(Authentication authentication, Long validTime) {

        return Jwts
                .builder()
                .subject(authentication.getName())
                // .claim(issur, validTime)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + validTime))
                .issuer(issur)
                .signWith(key)

                .compact();

    }

    // 토큰 검증 하는 메서드
    public void getAuthenticationByToken(String token) throws Exception {
        Claims claims = Jwts
                .parser()
                .verifyWith(this.key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        // 검증 실패하면 Exception 발생

    }

}
