package com.tyfff.musicapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tyfff.musicapi.domain.dto.User;

import java.util.List;

/**
* @author tyfff
* @description 针对表【user】的数据库操作Service
* @createDate 2023-03-19 12:03:37
*/
public interface UserService extends IService<User> {

    boolean registerUser(User user);

    String authenticateUser(String username, String password);

    List<User> getActiveUsers();
}
