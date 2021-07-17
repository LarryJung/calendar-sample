package com.fccalendar.core.exception;

import lombok.Getter;

@Getter
public class CalendarException extends RuntimeException {

    private ErrorCode errorCode;

    public CalendarException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
