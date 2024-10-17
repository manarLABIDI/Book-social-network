package com.mlsoftwares.book.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum BusinessErrorCodes {

    NO_CODE(0,NOT_IMPLEMENTED, "No code"),
    INCORRECT_CURRENT_PASSWORD(300,BAD_REQUEST, "Current password is incorrect"),
    NEW_PASSWORD_DOES_NOT_MATCH(301,BAD_REQUEST, "The new password does not match"),

    ACCOUNT_LOCKED(302,FORBIDDEN, "User account is locked"),
    ACCOUNT_DISABLED(303,BAD_REQUEST, "User account is disabled"),
    BAD_CREDENTIALS(304,FORBIDDEN, "login and / or password is incorrect"),
    ;

    @Getter
    private int code;
    @Getter
    private String description;
    @Getter
    private HttpStatus httpStatus;

    BusinessErrorCodes(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
