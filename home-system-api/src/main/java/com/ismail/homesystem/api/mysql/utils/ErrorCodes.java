package com.ismail.homesystem.api.mysql.utils;

import org.hibernate.exception.ConstraintViolationException;

public enum ErrorCodes {

    UNKNOWN_ERROR(),
    CONSTRAINT_VIOLATION();

    public static ErrorCodes getErrorCode(String errorString) {
        try {
            return ErrorCodes.valueOf(errorString);
        } catch (IllegalArgumentException e) {
            return UNKNOWN_ERROR;
        }
    }
}