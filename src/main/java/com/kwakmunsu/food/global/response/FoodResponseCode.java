package com.kwakmunsu.food.global.response;

import com.kwakmunsu.food.global.response.responseItem.StatusCode;
import com.kwakmunsu.food.global.response.responseItem.SuccessMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FoodResponseCode {
    // ===================== //

    // User 관련 성공 응답
    CREATED_USER(StatusCode.CREATED, SuccessMessage.CREATED_USER),
    READ_USER(StatusCode.OK, SuccessMessage.READ_USER),
    UPDATE_USER(StatusCode.NO_CONTENT, SuccessMessage.UPDATE_USER),
    DELETE_USER(StatusCode.NO_CONTENT, SuccessMessage.DELETE_USER),



    // ===================== //

    // vote 관련 성공 응답
    CREATED_VOTE(StatusCode.CREATED, SuccessMessage.CREATED_VOTE),
    SUBMITTED_VOTE(StatusCode.OK, SuccessMessage.SUBMITTED_VOTE),
    READ_VOTE(StatusCode.OK, SuccessMessage.READ_VOTE),
    READ_VOTES(StatusCode.OK, SuccessMessage.READ_VOTES),
    UPDATE_VOTE(StatusCode.NO_CONTENT, SuccessMessage.UPDATE_VOTE),
    DELETE_VOTE(StatusCode.NO_CONTENT, SuccessMessage.DELETE_VOTE),
    READ_VOTE_AUTHOR(StatusCode.OK,SuccessMessage.READ_VOTE_AUTHOR),



    // ===================== //

    // Token 성공 응답
    REISSUE_SUCCESS(StatusCode.OK, SuccessMessage.REISSUE_SUCCESS),
    // 토큰 유효 응답
    TOKEN_IS_VALID(StatusCode.OK, SuccessMessage.TOKEN_IS_VALID),






    // ===================== //

    // 기타 성공 응답
    READ_IS_LOGIN(StatusCode.OK, SuccessMessage.READ_IS_LOGIN),
    LOGIN_SUCCESS(StatusCode.OK, SuccessMessage.LOGIN_SUCCESS),
    USERNAME_SUCCESS(StatusCode.OK, SuccessMessage.USERNAME_SUCCESS),
    NICKNAME_SUCCESS(StatusCode.OK, SuccessMessage.NICKNAME_SUCCESS),
    LOGOUT_SUCCESS(StatusCode.OK, SuccessMessage.LOGOUT_SUCCESS),
    UPDATE_PASSWORD(StatusCode.NO_CONTENT, SuccessMessage.UPDATE_PASSWORD),



    ;


    private  int httpStatus;
    private String message;

}
