package com.tyfff.musicapi.config.interceptor;

import com.tyfff.musicapi.domain.dto.User;
import com.tyfff.musicapi.exception.AuthenticationException;
import com.tyfff.musicapi.service.UserService;
import com.tyfff.musicapi.utils.JwtUtil;
import com.tyfff.musicapi.utils.UserHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Enumeration;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    //处理请求之前调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        Enumeration<String> headerNames = request.getHeaderNames();
        Integer userId = JwtUtil.getUserIdByToken(token);
        if (userId == null) {
            throw new AuthenticationException();
        }
        User user = userService.getById(userId);
        if (user == null) {
            throw new AuthenticationException();
        }
        UserHolder.set(user);
        return true;

    }

    //处理之后
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserHolder.remove();
    }

    //请求整个流程结束调用
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
