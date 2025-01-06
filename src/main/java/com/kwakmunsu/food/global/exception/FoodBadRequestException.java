package com.kwakmunsu.food.global.exception;

import com.kwakmunsu.food.global.response.FoodErrorCode;
import com.kwakmunsu.food.global.response.FoodResponseCode;
import lombok.Getter;

@Getter
public class FoodBadRequestException extends FoodException {

    public FoodBadRequestException(FoodErrorCode errorCode) {
        super(errorCode);
    }

    public FoodBadRequestException(FoodErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
