package com.kwakmunsu.food.global.config.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwakmunsu.food.global.config.jwt.JwtProvider;
import com.kwakmunsu.food.global.config.jwt.response.JwtExceptionResponse;
import com.kwakmunsu.food.global.response.FoodErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    public static final String MEMBER_ID_ATTRIBUTE = "memberId";

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = resolveToken(request);
        // 빈 문자열("")**이나 공백만 있는 문자열은 false
        if (!StringUtils.hasText(token)) {
            sendErrorResponse(response, FoodErrorCode.WRONG_AUTH_HEADER);
            return;
        }
        // JWT에서 토큰을 이용해 인증 정보를 추출 후 UsernamePasswordAuthenticationToken을 생성해 전달
        // Authentication 객체를 생성하고, 이를 SecurityContext에 설정하여 이후의 요청에서 인증 정보를 사용할 수 있도록 힘
        try {
            jwtProvider.validateAccessToken(token);
            Authentication authentication = jwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            sendErrorResponse(response, FoodErrorCode.TOKEN_EXPIRED);
        } catch (Exception e) {
            sendErrorResponse(response, FoodErrorCode.TOKEN_ERROR);
        }
    }

    // 요청 헤더에 Authorization를 보면 접두사 Bearer가 포함되어있음. 제외하고 실제 Access 토큰을 가져오는 함수
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);  // 접두사 "Bearer "을 제외하고 실제 토큰 문자열을 반환.
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {
                "/auth/**","/swagger/**","/swagger-ui/**","/v3/api-docs/**",
                "/members/username/**","/members/nickname/**"
        };//"/v3/api-docs/**" 추가해줘야 swagger 작동
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }

    private void sendErrorResponse(HttpServletResponse response,FoodErrorCode foodErrorCode) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        JwtExceptionResponse responseJson = new JwtExceptionResponse(
                HttpStatus.valueOf(HttpServletResponse.SC_UNAUTHORIZED), // 상태 메시지
                foodErrorCode.getHttpStatus(),  // 커스텀 상태코드
                foodErrorCode.getMessage(), // 커스텀 메세지
                LocalDateTime.now().toString() // 오류 발생 시각
        );

        String jsonToString = objectMapper.writeValueAsString(responseJson);
        response.getWriter().write(jsonToString);
    }
}

