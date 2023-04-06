package com.tyfff.musicapi.utils;

import com.tyfff.musicapi.domain.dto.User;

public class UserHolder {
    private final static ThreadLocal<User> userIdHolder = new ThreadLocal<>();

    public static void set(User user){
        userIdHolder.set(user);
    }

    public static User get(){
        return userIdHolder.get();
    }

    public static void remove(){
        userIdHolder.remove();
    }
}
