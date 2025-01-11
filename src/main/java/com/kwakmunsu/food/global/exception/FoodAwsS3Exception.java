package com.kwakmunsu.food.global.exception;


import com.kwakmunsu.food.global.response.FoodErrorCode;
import lombok.Getter;

@Getter
public class FoodAwsS3Exception extends FoodException{

    public FoodAwsS3Exception(FoodErrorCode errorCode) {
        super(errorCode);
    }

    public FoodAwsS3Exception(FoodErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
