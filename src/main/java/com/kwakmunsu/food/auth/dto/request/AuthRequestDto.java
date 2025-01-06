package com.kwakmunsu.food.auth.dto.request;

import com.kwakmunsu.food.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthRequestDto {
        // ====== requestDto ======

        @Getter
        @NoArgsConstructor
        public static class SignUpRequest {

            private String username;
            private String password;
            private String nickname;

            public Member toMember(String password) {
             return   Member.builder()
                        .username(this.username)
                        .password(password)
                        .nickname(this.nickname)
                        .build();

            }

        }
        @Getter
        @NoArgsConstructor
        public static class ReissueRequest {

            private String accessToken;
        }

        @Getter
        @NoArgsConstructor
        public static class LoginRequest {

            private String username;
            private String password;
        }



}
