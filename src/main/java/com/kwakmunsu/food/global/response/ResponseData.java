package com.kwakmunsu.food.global.response;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

@Getter
@Builder
@ToString
public class ResponseData<T> {

    private final int status;
    private final String message;
    private final FoodResponseCode code;
    private T data;

    // response data가 없을때
    public static ResponseEntity<ResponseData> toResponseEntity(FoodResponseCode foodResponseCode) {
        return ResponseEntity
                .status(foodResponseCode.getHttpStatus())
                .body(ResponseData.builder()
                        .status(foodResponseCode.getHttpStatus())
                        .message(foodResponseCode.getMessage())
                        .code(foodResponseCode)
                        .build()
                );
    }

    // response data가 있을때
    public static <T> ResponseEntity<ResponseData<T>> toResponseEntity(
            FoodResponseCode foodResponseCode, T data) {
        return ResponseEntity
                .status(foodResponseCode.getHttpStatus())
                .body(ResponseData.<T>builder()
                        .status(foodResponseCode.getHttpStatus())
                        .message(foodResponseCode.getMessage())
                        .code(foodResponseCode)
                        .data(data)
                        .build()
                );
    }

    // response data와 header가 있을때
    public static <T> ResponseEntity<ResponseData<T>> toResponseEntity(
            FoodResponseCode foodResponseCode, MultiValueMap<String, String> header, T data) {
        return ResponseEntity
                .status(foodResponseCode.getHttpStatus())
                .header(String.valueOf(header))
                .body(ResponseData.<T>builder()
                        .status(foodResponseCode.getHttpStatus())
                        .message(foodResponseCode.getMessage())
                        .code(foodResponseCode)
                        .data(data)
                        .build()
                );
    }

}
