package com.kwakmunsu.food.global.exception;

import com.kwakmunsu.food.global.response.FoodErrorCode;
import lombok.Getter;

@Getter
public class FoodAuthenticationException extends FoodException {

    public FoodAuthenticationException(FoodErrorCode errorCode) {
        super(errorCode);
    }

    public FoodAuthenticationException(FoodErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
