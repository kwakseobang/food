package com.kwakmunsu.food.global.response.responseItem;

public class ErrorMessage {

    // < User -> Error >
    public static final String NOT_FOUND_USER = "ERROR - 회원을 찾을 수 없습니다.";
    public static final String BAD_REQUEST_USER = "ERROR - 잘못된 회원 요청 에러";
    public static final String BAD_REQUEST_PASSWORD = "ERROR - 잘못된 비밀번호 요청 에러";
    public static final String DUPLICATE_USERNAME = "ERROR - 회원가입 ID 중복 에러";
    public static final String DUPLICATE_NICKNAME = "ERROR - 회원가입 닉네임 중복 에러";



    // <Vote - Error>

    public static final String NOT_FOUND_FOOD_RECORDS = "ERROR - 해당 날짜의 음식 기록이 존재하지 않습니다..";
    public static final String NOT_FOUND_FOOD_RECORD = "ERROR -  음식 기록을 찾을 수 없습니다.";
    public static final String BAD_REQUEST_FOOD_RECORD = "ERROR - 잘못된 음식 기록 요청 에러";
    public static final String BAD_REQUEST_CATEGORY = "ERROR - 잘못된 음식 카테고리 요청 에러";


    // < Auth - Error>
    public static final String UNAUTHORIZED = "ERROR - Unauthorized 에러"; // 인증되지 않은 사용자
    public static final String FORBIDDEN = "ERROR - Forbidden 에러"; // 권한 없을 때
    public static final String PREVENT_GET_ERROR = "Status 204 - 리소스 및 리다이렉트 GET 호출 에러 방지";



    public static final String TOKEN_EXPIRED = "ERROR - JWT 토큰 만료 에러";
    public static final String TOKEN_ERROR = "ERROR - 잘못된 JWT 토큰 에러";
    public static final String BAD_REQUEST_TOKEN = "ERROR - 잘못된 토큰 요청 에러";

    public static final String TOKEN_IS_BLACKLIST = "ERROR - 폐기된 토큰";
    public static final String TOKEN_HASH_NOT_SUPPORTED = "ERROR - 지원하지 않는 형식의 토큰";
    public static final String WRONG_AUTH_HEADER = "ERROR - [Bearer ]로 시작하는 토큰이 없습니다.";
    public static final String TOKEN_VALIDATION_TRY_FAILED = "ERROR - 토큰 인증 실패";


    // < Etc >
    public static final String INTERNAL_SERVER_ERROR = "ERROR - 서버 내부 에러";
    public static final String AWS_S3_ERROR = "ERROR - AWS S3 내부 에러";
    public static final String AWS_S3_DELETE_ERROR = "ERROR - S3 이미지 삭제 에러";
    public static final String AWS_S3_UPLOAD_ERROR = "ERROR - S3 이미지 업로드 에러";
    public static final String AWS_S3_TYPE_ERROR = "ERROR - S3 허용되지 않는 파일 형식";

}
