package com.tyfff.musicapi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyfff.musicapi.domain.dto.UserSongHistory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tyfff.musicapi.domain.vo.PageVo;

import java.util.List;

/**
* @author tyfff
* @description 针对表【user_song_history】的数据库操作Service
* @createDate 2023-03-19 12:03:37
*/
public interface UserSongHistoryService extends IService<UserSongHistory> {



    PageVo<UserSongHistory> pageById(Integer userId, Page<UserSongHistory> userSongHistoryPage);

    List<UserSongHistory> findTopSongsByUserIdInGivenDays(Integer userId, int days, int limit);

    UserSongHistory getBySongId(String songId, Integer userId);

}
