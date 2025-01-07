package com.kwakmunsu.food.auth.presentation;


import static org.springframework.http.HttpHeaders.SET_COOKIE;

import com.kwakmunsu.food.auth.dto.request.AuthRequestDto;
import com.kwakmunsu.food.auth.dto.response.TokenResponseDto;
import com.kwakmunsu.food.auth.dto.response.TokenResponseDto.AccessTokenResponse;
import com.kwakmunsu.food.auth.jwt.domain.MemberTokens;
import com.kwakmunsu.food.auth.service.LoginService;
import com.kwakmunsu.food.global.response.FoodResponseCode;
import com.kwakmunsu.food.global.response.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth")
@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class LoginController {

    private final LoginService loginService;
    @PostMapping("/signup") // 중복 API는 따로 만듦. 해당 경로를 클라이언트에서 요청 시 유효하기 때문에 바로 DB 저장
    @Operation(summary = "회원가입 [JWT X]")
    public ResponseEntity<ResponseData> signup(
            @RequestBody AuthRequestDto.SignUpRequest signUpRequestDto
    ) {
        loginService.signup(signUpRequestDto);
        return ResponseData.toResponseEntity(FoodResponseCode.CREATED_USER);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인 [JWT X]")
    public ResponseEntity<ResponseData<TokenResponseDto.AccessTokenResponse>> login(
            @RequestBody AuthRequestDto.LoginRequest loginRequest,
            HttpServletResponse response
    ) {
        MemberTokens memberTokens = loginService.login(loginRequest);
        final ResponseCookie cookie = ResponseCookie.from("refresh", memberTokens.getRefreshToken())
                .maxAge(14 * 24 * 60 * 60)
                .sameSite("None")
//                .secure(true)
                .httpOnly(true)
                .path("/")
                .build();
        response.addHeader(SET_COOKIE, cookie.toString());
        return  ResponseData.toResponseEntity(
                FoodResponseCode.LOGIN_SUCCESS,
                new AccessTokenResponse(memberTokens.getAccessToken())
        );
    }
}
