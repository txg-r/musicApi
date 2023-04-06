package com.tyfff.musicapi.controller;

import com.tyfff.musicapi.domain.dto.User;
import com.tyfff.musicapi.domain.r.ResponseResult;
import com.tyfff.musicapi.service.UserService;
import com.tyfff.musicapi.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseResult<String> login( @RequestBody User user){
        String token = userService.authenticateUser(user.getUsername(), user.getPassword());
        if (token != null) {
            return ResponseResult.ok( "登录成功", token);
        } else {
            return ResponseResult.fail("登录失败，用户名或密码错误");
        }
    }

    @PostMapping("/register")
    public ResponseResult<Object> register(@RequestBody @Validated User user){
        if (userService.registerUser(user)){
            return ResponseResult.ok("注册成功");
        }else {
            return ResponseResult.fail("注册失败,用户名已存在");
        }
    }

    @GetMapping("/info")
    public ResponseResult<User> info(){
        User user = UserHolder.get();
        return ResponseResult.ok("查询用户信息成功",user);
    }
}
