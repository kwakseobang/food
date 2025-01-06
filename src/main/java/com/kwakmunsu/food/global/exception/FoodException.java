package com.kwakmunsu.food.global.exception;

import com.kwakmunsu.food.global.response.FoodErrorCode;
import lombok.Getter;

@Getter
public abstract class FoodException extends  RuntimeException{
    private FoodErrorCode errorCode;
    private String message;


    public FoodException(FoodErrorCode errorCode) {
        this.errorCode = errorCode;
    }
    public FoodException(FoodErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
