package com.kwakmunsu.food.global.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // 시큐리티에서 관리하게 됨
public class SecurityConfig {
    private final String[] adminUrl = {"/admin/**"};
    private final String[] permitAllUrl = {"/","/error","/auth/**","/swagger/**","/swagger-ui/**","/v3/api-docs/**"};
    private final String[] hasRoleUrl = {"/food/**"};

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 비활성화 필터
        http
                .formLogin((auth) -> auth.disable())
                .httpBasic((auth) -> auth.disable())
                .csrf((auth) -> auth.disable())
                .logout((auth) -> auth.disable()) // spring 컨테이너에서 처리
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 인가
        http
                .authorizeHttpRequests((auth) -> auth
                                .requestMatchers(permitAllUrl).permitAll() //모두 허용
                                .requestMatchers(adminUrl).hasRole("ADMIN") //
                                .requestMatchers(hasRoleUrl).hasAnyRole("ADMIN", "MEMBER")
                                .anyRequest().authenticated());// 위에서 처리 못한 나머지 경로를 로그인한 사용자만 접근 허용.
                        // 상단부터 check하게 됨.


        return http.build();
    }
}