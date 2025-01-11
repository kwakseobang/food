package com.kwakmunsu.food.global.exception.handler;


import com.kwakmunsu.food.global.exception.FoodException;
import com.kwakmunsu.food.global.response.ErrorResponse;
import com.kwakmunsu.food.global.response.FoodErrorCode;
import java.nio.file.AccessDeniedException;
import javax.naming.AuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<String>> handleException(Exception e) {
        return ErrorResponse.toResponseEntity(FoodErrorCode.INTERNAL_SERVER_ERROR,e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse<String>> handleUnauthorizedException(AuthenticationException e) {
        return ErrorResponse.toResponseEntity(FoodErrorCode.UNAUTHORIZED_ERROR,e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse<String>> handleForbiddenException(AccessDeniedException e) {
        return ErrorResponse.toResponseEntity(FoodErrorCode.FORBIDDEN_ERROR,e.getMessage());
    }


    // CustomException

    @ExceptionHandler(FoodException.class)
    public ResponseEntity<ErrorResponse<String>> handleCustomException(FoodException ex) {
        return ErrorResponse.toResponseEntity(ex.getErrorCode(),ex.getMessage());
    }
}
