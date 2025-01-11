package com.kwakmunsu.food.auth.jwt;


import com.kwakmunsu.food.auth.jwt.domain.MemberTokens;
import com.kwakmunsu.food.global.exception.FoodAuthenticationException;
import com.kwakmunsu.food.global.exception.FoodBadRequestException;
import com.kwakmunsu.food.global.response.FoodErrorCode;
import com.kwakmunsu.food.member.domain.Member;
import com.kwakmunsu.food.member.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

// @RequiredArgsConstructor 와 @Value는 같이 사용 못한다.  컴파일 타임에 Lombok과 충돌이 생길 수 있다.
@Component
public class JwtProvider {

    private final Long accessTokenExpireTime;
    private final Long refreshTokenExpireTime;
    private final SecretKey secretKey;
    private static final String MEMBER_ID_KEY = "id";
    private static final String CATEGORY_KEY = "category";
    private static final String AUTHORITIES_KEY = "auth";

    public JwtProvider(
            @Value("${spring.jwt.access.expiration}") Long accessTokenExpireTime,
            @Value("${spring.jwt.refresh.expiration}")  Long refreshTokenExpireTime,
            @Value("${spring.jwt.secretKey}")String secretKey
    ){
        this.accessTokenExpireTime = accessTokenExpireTime;
        this.refreshTokenExpireTime = refreshTokenExpireTime;
        this.secretKey =new SecretKeySpec(
                secretKey.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
    }

    
    public MemberTokens createTokens(Member member) {
        String accessToken = createToken(member.getId(),member.getRole(), "access",accessTokenExpireTime);
        String refreshToken = createToken(member.getId(), member.getRole(), "refresh",refreshTokenExpireTime);

        return new MemberTokens(refreshToken, accessToken);
    }

    private String createToken(Long memberId, Role role, String category, Long expiredMs) {
        Date date = new Date();
        Date validity = new Date(date.getTime() + expiredMs);

        String authority = role.toString();
        return Jwts.builder()
                .subject(String.valueOf(memberId))
                .claim(MEMBER_ID_KEY,memberId)
                .claim(CATEGORY_KEY, category)
                .claim(AUTHORITIES_KEY,authority)
                .expiration(validity)
                .signWith(secretKey)
                .compact();
    }


    // JWT에서 토큰을 이용해 인증 정보를 추출 후 UsernamePasswordAuthenticationToken을 생성해 전달
    // Authentication 객체를 생성하고, 이를 SecurityContext에 설정하여 이후의 요청에서 인증 정보를 사용할 수 있도록 함.
    public Authentication getAuthentication(String token) {
        String memberId  = getSubject(token);
        // 유저 권한은 하나밖에 없기에 singletonList로 진행함. 단 하나의 권한만 가질때 사용.
        String auth = getAuthority(token);
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(auth);
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(grantedAuthority);

        return new UsernamePasswordAuthenticationToken(memberId, null, authorities);
    }

    public boolean validateAccessToken(String accessToken) {
        parseToken(accessToken);
       return true;
    }
//
//    public boolean validateRefreshToken(String refreshToken) {
//        Claims claims =  parseToken(refreshToken);
//        return true;
//    }
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
//            return true;
//        }catch ()
//
//    }
    public String getSubject(final String token) {
        return parseToken(token)
                .getPayload()
                .getSubject();
    }
    public String getAuthority(final String token) {
        return parseToken(token)
                .getPayload()
                .get(AUTHORITIES_KEY, String.class);
    }
    // token 파싱
    public Jws<Claims> parseToken(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
        } catch (ExpiredJwtException e) {
            throw new FoodAuthenticationException(FoodErrorCode.TOKEN_EXPIRED);
        } catch (Exception e) {
            throw new FoodBadRequestException(FoodErrorCode.BAD_REQUEST_TOKEN, e.getMessage());
        }
    }
}
