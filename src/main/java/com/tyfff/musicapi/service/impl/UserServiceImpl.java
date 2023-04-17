package com.tyfff.musicapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyfff.musicapi.domain.dto.User;
import com.tyfff.musicapi.service.UserService;
import com.tyfff.musicapi.mapper.UserMapper;
import com.tyfff.musicapi.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tyfff
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-03-19 12:03:37
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {


    @Autowired
    private MusicRecommendationService musicRecommendationService;

    @Override
    public boolean registerUser(User user) {
        User existUser = baseMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, user.getUsername()));
        if (existUser!=null) {
            return false;
        }

        baseMapper.insert(user);

        //新用户创建每日推荐歌单
        musicRecommendationService.createUserDayRecommendations(user.getUserId());

        return true;

    }

    @Override
    public String authenticateUser(String username, String password) {
        User user = baseMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
                .eq(User::getPassword, password));
        return user == null ? null : JwtUtil.getJwtToken(user.getUserId());
    }

    @Override
    public List<User> getActiveUsers() {
        return baseMapper.selectByLastLoginOnDay(7);
    }
}




