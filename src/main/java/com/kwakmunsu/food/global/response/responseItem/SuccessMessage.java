package com.kwakmunsu.food.global.response.responseItem;

public class SuccessMessage {

    // < User - Success>
    public static final String CREATED_MEMBER = "SUCCESS - 회원 가입 성공";
    public static final String READ_MEMBER = "SUCCESS - 회원 정보 조회 성공";
    public static final String UPDATE_MEMBER = "SUCCESS - 회원 정보 수정 성공";
    public static final String DELETE_MEMBER = "SUCCESS - 회원 탈퇴 성공";




    // < Vote - Success>
    public static final String CREATED_VOTE = "SUCCESS - 투표 생성 성공";
    public static final String SUBMITTED_VOTE = "SUCCESS - 투표 제출 성공";
    public static final String READ_VOTE = "SUCCESS - 투표 정보 조회 성공";
    public static final String READ_VOTES = "SUCCESS - 투표 목록 조회 성공";
    public static final String UPDATE_VOTE = "SUCCESS - 투표 수정 성공";
    public static final String READ_VOTE_AUTHOR = "SUCCESS - 투표 작성자 조회 성공";
    public static final String DELETE_VOTE = "SUCCESS - 투표 삭제 성공";






    // < Auth - Success>
    public static final String LOGIN_SUCCESS = "SUCCESS - 로그인 성공";
    public static final String LOGOUT_SUCCESS = "SUCCESS - 로그아웃 성공 및 user Refresh Token 삭제";
    public static final String UPDATE_PASSWORD = "SUCCESS - 비밀번호 수정 성공";
    public static final String USERNAME_SUCCESS = "SUCCESS - 사용 가능한 아이디입니다.";
    public static final String NICKNAME_SUCCESS = "SUCCESS - 사용 가능한 닉네임입니다.";
    public static final String READ_IS_LOGIN = "SUCCESS - 현재 로그인 여부 조회 성공";


    // < Token >
    public static final String TOKEN_IS_VALID = "VALID - 유효한 토큰  ";
    public static final String REISSUE_SUCCESS = "SUCCESS - JWT Access 토큰 재발급 성공";

}
