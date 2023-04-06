package com.tyfff.musicapi.exception;

public class BaseException extends RuntimeException{
    private int errorCode;

    public BaseException() {
        super();
    }

    public BaseException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
