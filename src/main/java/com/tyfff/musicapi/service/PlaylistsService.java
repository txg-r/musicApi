package com.tyfff.musicapi.service;

import com.tyfff.musicapi.domain.dto.Playlists;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tyfff.musicapi.domain.dto.SongData;

import java.util.List;

/**
* @author tyfff
* @description 针对表【playlists】的数据库操作Service
* @createDate 2023-03-19 12:03:37
*/
public interface PlaylistsService extends IService<Playlists> {

    void createDayRecommendations(Integer userId, List<SongData> dayRecommend);
}
