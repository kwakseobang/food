package com.kwakmunsu.food.global.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@ToString
public class ErrorResponse {
    private final int status;
    private final String message;
    private final FoodErrorCode code;
    // response data가 없을때
    public static ResponseEntity<ErrorResponse> toResponseEntity(FoodErrorCode foodErrorCode) {
        return ResponseEntity
                .status(foodErrorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(foodErrorCode.getHttpStatus())
                        .message(foodErrorCode.getMessage())
                        .code(foodErrorCode)
                        .build()
                );
    }
}
