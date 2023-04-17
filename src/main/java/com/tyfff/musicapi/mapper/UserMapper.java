package com.tyfff.musicapi.mapper;

import com.tyfff.musicapi.domain.dto.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author tyfff
 * @description 针对表【user】的数据库操作Mapper
 * @createDate 2023-04-14 16:46:40
 * @Entity com.tyfff.musicapi.domain.dto.User
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM user WHERE last_login >= DATE_SUB(NOW(), INTERVAL #{day} DAY)")
    public List<User> selectByLastLoginOnDay(@Param("day") int day);

}




