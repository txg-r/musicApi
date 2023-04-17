package com.tyfff.musicapi.mapper;

import com.tyfff.musicapi.domain.dto.UserPlaylistSongs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author tyfff
* @description 针对表【user_playlist_songs】的数据库操作Mapper
* @createDate 2023-03-19 12:03:37
* @Entity com.tyfff.musicapi.domain.dto.UserPlaylistSongs
*/
public interface UserPlaylistSongsMapper extends BaseMapper<UserPlaylistSongs> {

    @Select("SELECT ups.* FROM user_playlist_songs ups " +
            "INNER JOIN playlists p ON ups.playlist_id = p.playlist_id " +
            "WHERE p.playlist_name = '每日推荐' AND DATE(ups.added_at) = CURDATE() " +
            "AND ups.user_id = #{userId}")
    List<UserPlaylistSongs> selectDayRecommend(@Param("userId") Integer userId);
}




