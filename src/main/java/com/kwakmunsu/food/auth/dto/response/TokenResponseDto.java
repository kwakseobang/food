package com.kwakmunsu.food.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TokenResponseDto {

    // ======== < Response DTO > ======== //

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccessTokenResponse {
        private String accessToken;
    }
    @Builder
    @Getter
    @AllArgsConstructor
    public static class RefreshTokenResponse {
        // rt는 쿠키에 담아서 보내는데 발급 후 service단에서 가져오기 위해서 만듦
        private String refreshToken;
    }
}
