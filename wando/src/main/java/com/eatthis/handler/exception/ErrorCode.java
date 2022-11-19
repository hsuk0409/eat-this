package com.eatthis.handler.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    INVALID_AUTHORIZATION(UNAUTHORIZED, null),

    PARAMETER_IS_REQUIRED(BAD_REQUEST, null),
    INVALID_PARAMETER(BAD_REQUEST, null),

    NOT_FOUND_ACCOUNT(NOT_FOUND, null),
    NOT_FOUND_DEVICE(NOT_FOUND, null)

    ;

    private final HttpStatus httpStatus;
    private String detail;

    ErrorCode(HttpStatus httpStatus, String detail) {
        this.httpStatus = httpStatus;
        this.detail = detail;
    }

    public void setCustomDetail(String message) {
        this.detail = message;
    }

}
