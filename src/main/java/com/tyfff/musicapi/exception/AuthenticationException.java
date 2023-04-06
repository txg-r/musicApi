package com.tyfff.musicapi.exception;

public class AuthenticationException extends BaseException{

    public AuthenticationException() {
        super("认证失败",2);
    }
}
