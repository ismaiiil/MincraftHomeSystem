package com.ismail.homesystem.api.mysql.utils;

import org.hibernate.exception.ConstraintViolationException;

public enum ErrorCodes {

    UNKNOWN_ERROR(),
    CONSTRAINT_VIOLATION();

    public static ErrorCodes getErrorCode(Object exception) {
        if (exception instanceof ConstraintViolationException) {
            return CONSTRAINT_VIOLATION;
        }
        return UNKNOWN_ERROR; // Default error code if no matching exception type is found
    }
}