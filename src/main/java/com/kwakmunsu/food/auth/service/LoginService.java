package com.kwakmunsu.food.auth.service;


import com.kwakmunsu.food.auth.dto.request.AuthRequestDto;
import com.kwakmunsu.food.auth.dto.response.TokenResponseDto;
import com.kwakmunsu.food.auth.jwt.JwtProvider;
import com.kwakmunsu.food.auth.jwt.domain.MemberTokens;
import com.kwakmunsu.food.auth.jwt.domain.RefreshToken;
import com.kwakmunsu.food.auth.jwt.repository.RefreshTokenRepository;
import com.kwakmunsu.food.global.exception.FoodException;
import com.kwakmunsu.food.global.exception.FoodNotFoundException;
import com.kwakmunsu.food.global.response.FoodErrorCode;
import com.kwakmunsu.food.member.domain.Member;
import com.kwakmunsu.food.member.repositroy.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signup(AuthRequestDto.SignUpRequest signUpRequest) {
        String encodedPassword = bCryptPasswordEncoder.encode(signUpRequest.getPassword());

        memberRepository.save(signUpRequest.toMember(encodedPassword));
    }

    @Transactional
    public MemberTokens login(
            AuthRequestDto.LoginRequest loginRequest
            ) {
        Member member = memberRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new FoodNotFoundException(FoodErrorCode.NOT_FOUND_USER));

        MemberTokens memberTokens = jwtProvider.createTokens(member.getId());
        RefreshToken refreshToken = new RefreshToken( memberTokens.getRefreshToken(), member.getId());
        refreshTokenRepository.save(refreshToken);
        return memberTokens;
    }

}
