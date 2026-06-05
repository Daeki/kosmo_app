package com.witer.app.security;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.witer.app.members.MemberDTO;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private ObjectMapper mapper;
    private AuthenticationManager authenticationManager;
    private JwtTokenManager jwtTokenManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenManager jwtTokenManager) {
        this.authenticationManager = authenticationManager;
        this.setFilterProcessesUrl("/member/login");
        this.mapper = new ObjectMapper();
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        // 1. 요청 정보에서 ID와 PW를 꺼내 오기
        // {username:XXX, password:XXX}

        try {
            MemberDTO memberDTO = mapper.readValue(request.getInputStream(), MemberDTO.class);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(memberDTO.getUsername(),
                    memberDTO.getPassword());
            return authenticationManager.authenticate(token);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 로그인 성공 했을 때
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        // 토큰을 생성하고 응답으로 전송
        String accessToken = jwtTokenManager.createAccessToken(authResult);
        String refreshToken = jwtTokenManager.createRefreshToken(authResult);

        JwtAuthDTO jwtAuthDTO = new JwtAuthDTO();
        jwtAuthDTO.setAccessToken(accessToken);
        jwtAuthDTO.setRefreshToken(refreshToken);
        jwtAuthDTO.setUsername(authResult.getName());

        // JSON 형태의 String으로 변환
        String result = mapper.writeValueAsString(jwtAuthDTO);

        response.getWriter().write(result);

    }

    // 로그인 실패 했을 때
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {

    }

}
