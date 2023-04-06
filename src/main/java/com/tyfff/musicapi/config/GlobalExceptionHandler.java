package com.tyfff.musicapi.config;

import com.tyfff.musicapi.constant.ResponseCodeEnums;
import com.tyfff.musicapi.domain.r.ResponseResult;
import com.tyfff.musicapi.exception.AuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseResult<Object> authenticationExceptionHandler(AuthenticationException e){
        return new ResponseResult<>(e.getErrorCode(),e.getMessage(),null);
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseResult<List<String>> validExceptionHandler(BindException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<String> list = fieldErrors.stream().map(fieldError -> {
            return fieldError.getField() + " : " + fieldError.getDefaultMessage();
        }).collect(Collectors.toList());
        return new ResponseResult<>(1,"参数校验失败",list);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseResult<Void> runtimeExceptionHandler(RuntimeException e){
        e.printStackTrace();
        return new ResponseResult<>(1,e.getMessage(),null);
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult<Object> defaultExceptionHandler(Exception e){
        e.printStackTrace();
        return ResponseResult.fail(ResponseCodeEnums.HTTP_STATUS_500);
    }
}
