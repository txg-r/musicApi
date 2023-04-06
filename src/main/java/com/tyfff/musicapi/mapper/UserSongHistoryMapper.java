package com.tyfff.musicapi.mapper;

import com.tyfff.musicapi.domain.dto.UserSongHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author tyfff
* @description 针对表【user_song_history】的数据库操作Mapper
* @createDate 2023-03-19 12:03:37
* @Entity com.tyfff.musicapi.domain.dto.UserSongHistory
*/
public interface UserSongHistoryMapper extends BaseMapper<UserSongHistory> {

    @Select("SELECT * " +
            "FROM user_song_history " +
            "WHERE user_id = #{userId} AND last_listened_at >= NOW() - INTERVAL #{days} DAY " +
            "ORDER BY listen_count DESC, last_listened_at DESC " +
            "LIMIT #{limit}")
    List<UserSongHistory> findTopSongsByUserIdInGivenDays(@Param("userId") Integer userId, @Param("days") int days, @Param("limit") int limit);
}




