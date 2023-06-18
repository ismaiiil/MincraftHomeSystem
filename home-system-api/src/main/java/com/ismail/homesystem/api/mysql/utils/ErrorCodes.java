package com.ismail.homesystem.api.mysql.utils;

public enum ErrorCodes {

    UNHANDLED_ERROR(),
    NOT_FOUND(),
    CONSTRAINT_VIOLATION();

    public static ErrorCodes getErrorCode(String errorString) {
        try {
            return ErrorCodes.valueOf(errorString);
        } catch (IllegalArgumentException e) {
            return UNHANDLED_ERROR;
        }
    }
}