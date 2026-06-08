package com.witer.app.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private JwtTokenManager jwtTokenManager;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        security
                .csrf(c -> c.disable())
                .cors(c -> c.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(a -> {
                    a
                            //.requestMatchers("/notice/add").hasRole("ADMIN")
                            .anyRequest().permitAll();

                })
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager(),
                        jwtTokenManager));

        ;
        return security.build();
    }

    // 패스워드 암호화와 검증
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // CORS Filter
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 허용할 출발지 URL 목록 (Origin은 프로토콜, 도메인, 포트만 포함해야 하며 경로를 포함하면 안 됩니다)
        configuration.setAllowedOriginPatterns(
                Arrays.asList(
                        "http://localhost:5173",
                        "http://192.168.0.90:5173"));

        // 허용할 메서드 목록
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

        // 허용할 Header 목록
        configuration.setAllowedHeaders(Arrays.asList(
                "Authorization", "Content-Type", "Content-type", "Cache-Control"));

        // 응답시 Header 허용
        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        // configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
