package com.tyfff.musicapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyfff.musicapi.domain.dto.User;
import com.tyfff.musicapi.service.UserService;
import com.tyfff.musicapi.mapper.UserMapper;
import com.tyfff.musicapi.utils.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author tyfff
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-03-19 12:03:37
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Override
    public boolean registerUser(User user) {
        if (baseMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername,user.getUsername()))!=null) {
            return false;
        }
        return baseMapper.insert(user)==1;

    }

    @Override
    public String authenticateUser(String username, String password) {
        User user = baseMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
                .eq(User::getPassword, password));
        return user == null ? null : JwtUtil.getJwtToken(user.getUserId());
    }
}




