package com.eatthis.handler.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
public enum ErrorCode {

    PARAMETER_IS_REQUIRED(BAD_REQUEST, null),
    INVALID_PARAMETER(BAD_REQUEST, null)
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
