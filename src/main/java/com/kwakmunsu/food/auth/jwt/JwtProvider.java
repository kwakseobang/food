package com.kwakmunsu.food.auth.jwt;


import com.kwakmunsu.food.auth.jwt.domain.MemberTokens;
import com.kwakmunsu.food.global.exception.FoodBadRequestException;
import com.kwakmunsu.food.global.response.FoodErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

// @RequiredArgsConstructor 와 @Value는 같이 사용 못한다.  컴파일 타임에 Lombok과 충돌이 생길 수 있다.
@Component
public class JwtProvider {

    private final Long accessTokenExpireTime;
    private final Long refreshTokenExpireTime;
    private final SecretKey secretKey;
    private static final String MEMBER_ID_KEY = "id";
    private static final String CATEGORY_KEY = "category";

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

    
    public MemberTokens createTokens(Long memberId) {
        String accessToken = createToken(memberId, "access",accessTokenExpireTime);
        String refreshToken = createToken(memberId, "refresh",refreshTokenExpireTime);

        return new MemberTokens(refreshToken, accessToken);
    }

    private String createToken(Long memberId,String category, Long expiredMs) {
        Date date = new Date();
        Date validity = new Date(date.getTime() + expiredMs);
        return Jwts.builder()
                .subject(String.valueOf(memberId))
                .claim(MEMBER_ID_KEY,memberId)
                .claim(CATEGORY_KEY, category)
                .expiration(validity)
                .signWith(secretKey)
                .compact();
    }

    // JWT에서 토큰을 이용해 인증 정보를 추출 후 UsernamePasswordAuthenticationToken을 생성해 전달
    // Authentication 객체를 생성하고, 이를 SecurityContext에 설정하여 이후의 요청에서 인증 정보를 사용할 수 있도록 함.
    public Authentication getAuthentication(String token) {

        String memberId  = parseToken(token).getSubject();

        return new UsernamePasswordAuthenticationToken(memberId, null, null);
    }

    public boolean validateAccessToken(String accessToken) {
        Claims claims =  parseToken(accessToken);
       return true;
    }

    public boolean validateRefreshToken(String refreshToken) {
        Claims claims =  parseToken(refreshToken);
        return true;
    }
    // token 파싱
    private Claims parseToken(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (Exception e) {
            throw new FoodBadRequestException(FoodErrorCode.BAD_REQUEST_TOKEN, e.getMessage());
        }
    }
}
