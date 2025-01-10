package com.kwakmunsu.food.global.exception;


import com.kwakmunsu.food.global.response.ErrorResponse;
import com.kwakmunsu.food.global.response.FoodErrorCode;
import java.nio.file.AccessDeniedException;
import javax.naming.AuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
//@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException() {
        return ErrorResponse.toResponseEntity(FoodErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException() {
        return ErrorResponse.toResponseEntity(FoodErrorCode.UNAUTHORIZED_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException() {
        return ErrorResponse.toResponseEntity(FoodErrorCode.FORBIDDEN_ERROR);
    }


    // CustomException

    @ExceptionHandler(FoodException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(FoodException ex) {
        return ErrorResponse.toResponseEntity(ex.getErrorCode());
    }
}
