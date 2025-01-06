package com.kwakmunsu.food.global.exception;

import com.kwakmunsu.food.global.response.FoodErrorCode;
import lombok.Getter;

@Getter
public class FoodNotFoundException extends FoodException {
    public FoodNotFoundException(FoodErrorCode errorCode) {
        super(errorCode);
    }
    public FoodNotFoundException(FoodErrorCode errorCode, String message) {
        super(errorCode, message);
    }


}
