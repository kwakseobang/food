package com.kwakmunsu.food.global.response;

import com.kwakmunsu.food.global.response.responseItem.ErrorMessage;
import com.kwakmunsu.food.global.response.responseItem.StatusCode;
import com.kwakmunsu.food.global.response.responseItem.SuccessMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode  {
    // ===================== //

    // User 관련 성공 응답
    CREATED_USER(StatusCode.CREATED, SuccessMessage.CREATED_USER),
    READ_USER(StatusCode.OK, SuccessMessage.READ_USER),
    UPDATE_USER(StatusCode.NO_CONTENT, SuccessMessage.UPDATE_USER),
    DELETE_USER(StatusCode.NO_CONTENT, SuccessMessage.DELETE_USER),

    // User 관련 실패 응답
    NOT_FOUND_USER(StatusCode.NOT_FOUND, ErrorMessage.NOT_FOUND_USER),
    BAD_REQUEST_USER(StatusCode.BAD_REQUEST, ErrorMessage.BAD_REQUEST_USER),
    DUPLICATE_ID(StatusCode.BAD_REQUEST, ErrorMessage.DUPLICATE_ID),
    DUPLICATE_NICKNAME(StatusCode.BAD_REQUEST, ErrorMessage.DUPLICATE_NICKNAME),

    // ===================== //

    // vote 관련 성공 응답
    CREATED_VOTE(StatusCode.CREATED, SuccessMessage.CREATED_VOTE),
    SUBMITTED_VOTE(StatusCode.OK, SuccessMessage.SUBMITTED_VOTE),
    READ_VOTE(StatusCode.OK, SuccessMessage.READ_VOTE),
    READ_VOTES(StatusCode.OK, SuccessMessage.READ_VOTES),
    UPDATE_VOTE(StatusCode.NO_CONTENT, SuccessMessage.UPDATE_VOTE),
    DELETE_VOTE(StatusCode.NO_CONTENT, SuccessMessage.DELETE_VOTE),
    READ_VOTE_AUTHOR(StatusCode.OK,SuccessMessage.READ_VOTE_AUTHOR),

    // vote 관련 실패 응답
    NOT_FOUND_VOTE(StatusCode.NOT_FOUND, ErrorMessage.NOT_FOUND_VOTE),
    BAD_REQUEST_VOTE(StatusCode.BAD_REQUEST, ErrorMessage.BAD_REQUEST_VOTE),

    // ===================== //

    // Token 성공 응답
    REISSUE_SUCCESS(StatusCode.OK, SuccessMessage.REISSUE_SUCCESS),
    // 토큰 유효 응답
    TOKEN_IS_VALID(StatusCode.OK, SuccessMessage.TOKEN_IS_VALID),



    // Token 실패 응답
    TOKEN_EXPIRED(StatusCode.TOKEN_EXPIRED, ErrorMessage.TOKEN_EXPIRED),
    TOKEN_ERROR(StatusCode.TOKEN_ERROR, ErrorMessage.TOKEN_ERROR),
    BAD_REQUEST_TOKEN(StatusCode.BAD_REQUEST, ErrorMessage.BAD_REQUEST_TOKEN),
    TOKEN_IS_BLACKLIST(StatusCode.TOKEN_IS_BLACKLIST,ErrorMessage.TOKEN_IS_BLACKLIST),
    TOKEN_HASH_NOT_SUPPORTED(StatusCode.TOKEN_HASH_NOT_SUPPORTED,ErrorMessage.TOKEN_HASH_NOT_SUPPORTED),
    WRONG_AUTH_HEADER(StatusCode.NO_AUTH_HEADER,ErrorMessage.WRONG_AUTH_HEADER),
    TOKEN_VALIDATION_TRY_FAILED(StatusCode.TOKEN_VALIDATION_TRY_FAILED,ErrorMessage.TOKEN_VALIDATION_TRY_FAILED),


    // ===================== //

    // 기타 성공 응답
    READ_IS_LOGIN(StatusCode.OK, SuccessMessage.READ_IS_LOGIN),
    LOGIN_SUCCESS(StatusCode.OK, SuccessMessage.LOGIN_SUCCESS),
    USERNAME_SUCCESS(StatusCode.OK, SuccessMessage.USERNAME_SUCCESS),
    NICKNAME_SUCCESS(StatusCode.OK, SuccessMessage.NICKNAME_SUCCESS),
    LOGOUT_SUCCESS(StatusCode.OK, SuccessMessage.LOGOUT_SUCCESS),
    UPDATE_PASSWORD(StatusCode.NO_CONTENT, SuccessMessage.UPDATE_PASSWORD),


    // 기타 실패 응답
    PREVENT_GET_ERROR(StatusCode.NO_CONTENT, ErrorMessage.PREVENT_GET_ERROR),
    INTERNAL_SERVER_ERROR(StatusCode.INTERNAL_SERVER_ERROR, ErrorMessage.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED_ERROR(StatusCode.UNAUTHORIZED, ErrorMessage.UNAUTHORIZED),
    FORBIDDEN_ERROR(StatusCode.FORBIDDEN, ErrorMessage.FORBIDDEN),
    ;


    private  int httpStatus;
    private String message;

}