package com.tyfff.musicapi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyfff.musicapi.domain.dto.UserPlaylistSongs;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tyfff.musicapi.domain.r.ResponseResult;
import com.tyfff.musicapi.domain.vo.PageVo;

import java.util.List;

/**
* @author tyfff
* @description 针对表【user_playlist_songs】的数据库操作Service
* @createDate 2023-03-19 12:03:37
*/
public interface UserPlaylistSongsService extends IService<UserPlaylistSongs> {


    ResponseResult<Void> createFavoriteSong(Integer userId, String songId);

    ResponseResult<Void> removeFavoriteSong(Integer userId, String songId);

    PageVo<UserPlaylistSongs> pageFavoriteByUserId(Integer userId, Page<UserPlaylistSongs> userPlaylistSongsPage);

    List<UserPlaylistSongs> getDayRecommend(Integer userId);
}
