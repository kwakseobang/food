package com.kwakmunsu.food.auth.service;


import static org.springframework.http.HttpHeaders.SET_COOKIE;

import com.kwakmunsu.food.auth.dto.request.AuthRequestDto;
import com.kwakmunsu.food.auth.jwt.JwtProvider;
import com.kwakmunsu.food.auth.jwt.domain.MemberTokens;
import com.kwakmunsu.food.auth.jwt.domain.RefreshToken;
import com.kwakmunsu.food.auth.jwt.repository.RefreshTokenRepository;
import com.kwakmunsu.food.global.exception.FoodBadRequestException;
import com.kwakmunsu.food.global.exception.FoodNotFoundException;
import com.kwakmunsu.food.global.response.FoodErrorCode;
import com.kwakmunsu.food.member.domain.Member;
import com.kwakmunsu.food.member.repositroy.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
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

    //  UserDetailsService, UserDetails을 필수적으로 구현해야돼ㅣ는 것은 아니다. 이번엔 그냥 loginservice에서 사용자 정보 조회함.
    @Transactional
    public MemberTokens login(
            AuthRequestDto.LoginRequest loginRequest,
            HttpServletResponse response
    ) {
        Member member = memberRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new FoodNotFoundException(FoodErrorCode.NOT_FOUND_MEMBER));
        // mathes(평문 패스워드, 암호화 패스워드) 순서로 해야 됨.
        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
            throw new FoodBadRequestException(FoodErrorCode.BAD_REQUEST_PASSWORD);
        }
        return getMemberTokens(response, member);
    }



    @Transactional
    public MemberTokens reissue(
            String refreshTokenRequest,
            HttpServletResponse response
    ) {
        String memberId = jwtProvider.getSubject(refreshTokenRequest);
        Member member = memberRepository.findById(Long.parseLong(memberId))
                .orElseThrow(() -> new FoodNotFoundException(FoodErrorCode.NOT_FOUND_MEMBER));
        log.info("요청 토큰: " + refreshTokenRequest);

        if( !refreshTokenRepository.existsById(refreshTokenRequest) ) {
                throw  new FoodBadRequestException(FoodErrorCode.BAD_REQUEST_TOKEN);
        }
        refreshTokenRepository.deleteById(refreshTokenRequest); // refreshtoken 일회용으로 사용하기 때문에 삭제
        return getMemberTokens(response, member);
    }



    // =================== 유틸성 메소드 ===================
    private RefreshToken createRefreshToken(String token, long memberId) {
        return RefreshToken.RefreshTokenSaveBuilder()
                .token(token)
                .memberId(memberId)
                .build();
    }

    private ResponseCookie crateCookie(final String refreshToken) {
        return  ResponseCookie.from("refresh",refreshToken)
                .maxAge(14 * 24 * 60 * 60)
                .sameSite("None")
//                .secure(true)
                .httpOnly(true)
                .path("/")
                .build();
    }

    private MemberTokens getMemberTokens(HttpServletResponse response, Member member) {
        MemberTokens memberTokens = jwtProvider.createTokens(member);
        RefreshToken refreshToken = createRefreshToken(
                memberTokens.getRefreshToken(),
                member.getId()
        );
        ResponseCookie responseCookie = crateCookie(refreshToken.getToken());
        response.addHeader(SET_COOKIE, responseCookie.toString());
        refreshTokenRepository.save(refreshToken);
        return memberTokens;
    }

}
