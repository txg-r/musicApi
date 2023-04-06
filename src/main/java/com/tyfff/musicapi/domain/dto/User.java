package com.tyfff.musicapi.domain.dto;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer userId;

    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度应为3-20个字符")
    private String username;

    @NotBlank(message = "电子邮件不能为空")
    @Email(message = "电子邮件格式不正确")
    private String email;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度应为6-20个字符")
    private String password;


    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}