package com.eatthis.handler.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException exception) {
        log.error("handleCustomException throw CustomException: {}", exception.getErrorCode());
        return ErrorResponse.toResponseEntity(exception.getErrorCode());
    }

}
