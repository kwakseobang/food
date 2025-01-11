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
    CREATED_MEMBER(StatusCode.CREATED, SuccessMessage.CREATED_MEMBER),
    READ_MEMBER(StatusCode.OK, SuccessMessage.READ_MEMBER),
    UPDATE_MEMBER(StatusCode.NO_CONTENT, SuccessMessage.UPDATE_MEMBER),
    DELETE_MEMBER(StatusCode.NO_CONTENT, SuccessMessage.DELETE_MEMBER),



    // ===================== //

    // 음식 기록 관련 성공 응답
    CREATED_FOOD_RECORD(StatusCode.CREATED, SuccessMessage.CREATED_RECORD),
    SUBMITTED_FOOD_RECORD(StatusCode.OK, SuccessMessage.SUBMITTED_RECORD),
    READ_FOOD_RECORD(StatusCode.OK, SuccessMessage.READ_RECORD),
    READ_FOOD_RECORDS(StatusCode.OK, SuccessMessage.READ_RECORDS),
    UPDATE_FOOD_RECORD(StatusCode.NO_CONTENT, SuccessMessage.UPDATE_RECORD),
    DELETE_FOOD_RECORD(StatusCode.NO_CONTENT, SuccessMessage.DELETE_RECORD),
    READ_FOOD_RECORD_AUTHOR(StatusCode.OK,SuccessMessage.READ_RECORD_AUTHOR),



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
