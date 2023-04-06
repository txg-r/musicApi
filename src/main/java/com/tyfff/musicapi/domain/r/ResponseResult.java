package com.tyfff.musicapi.domain.r;

import com.tyfff.musicapi.constant.ResponseCodeEnums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult<T>{

    private int code;

    private String message;

    private T data;

    public static <T> ResponseResult<T> ok(){
        return new ResponseResult<>(ResponseCodeEnums.SUCCESS.getCode(),
                ResponseCodeEnums.SUCCESS.getMessage(), null);
    }

    public static <T> ResponseResult<T> ok(String message){
        return new ResponseResult<>(ResponseCodeEnums.SUCCESS.getCode(),
                message,null);
    }

    public static <T> ResponseResult<T> ok(String message,T data){
        return new ResponseResult<>(ResponseCodeEnums.SUCCESS.getCode(),
                message,data);
    }

    //http类错误
    public static <T> ResponseResult<T> fail(ResponseCodeEnums enums){
        return new ResponseResult<>(enums.getCode(), enums.getMessage(), null);
    }

    //业务类错误
    public static <T> ResponseResult<T> fail(String message){
        return new ResponseResult<>(0,message,null);
    }




}
