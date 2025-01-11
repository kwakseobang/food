package com.kwakmunsu.food.food.type;

import com.kwakmunsu.food.global.exception.FoodBadRequestException;
import com.kwakmunsu.food.global.response.FoodErrorCode;
import lombok.Getter;

@Getter
public enum FoodTypeEnum {
    ITALIAN("양식"),
    CHINESE("중식"),
    KOREAN("한식"),
    JAPANESE("일식"),
    FAST_FOOD("패스트푸드"),
    DESSERT("디저트"),
    OTHER("기타");

    // 문자열을 저장할 필드
    private String foodType;

    // 생성자 (싱글톤)
    private FoodTypeEnum(String foodType) {
        this.foodType = foodType;
    }
    // 클라이언트에서 전달된 한글 값을 FoodTypeEnum으로 변환하는 메서드
    public static FoodTypeEnum fromString(String foodType) {
        for (FoodTypeEnum type : values()) {
            if (type.foodType.equals(foodType)) {
                return type;
            }
        }
        throw new FoodBadRequestException(FoodErrorCode.BAD_REQUEST_CATEGORY, "Invalid FoodType: " + foodType);
    }

}
