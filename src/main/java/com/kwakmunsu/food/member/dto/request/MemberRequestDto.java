package com.kwakmunsu.food.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;


public class MemberRequestDto {

    @Getter
    @NoArgsConstructor
    public static class UpdateInfoRequest {

       private String nickname;

    }

}
