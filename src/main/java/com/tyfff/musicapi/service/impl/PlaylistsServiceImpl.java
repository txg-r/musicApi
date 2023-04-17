package com.tyfff.musicapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyfff.musicapi.domain.dto.Playlists;
import com.tyfff.musicapi.domain.dto.SongData;
import com.tyfff.musicapi.domain.dto.User;
import com.tyfff.musicapi.domain.dto.UserPlaylistSongs;
import com.tyfff.musicapi.service.PlaylistsService;
import com.tyfff.musicapi.mapper.PlaylistsMapper;
import com.tyfff.musicapi.service.UserPlaylistSongsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tyfff
 * @description 针对表【playlists】的数据库操作Service实现
 * @createDate 2023-03-19 12:03:37
 */
@Service
public class PlaylistsServiceImpl extends ServiceImpl<PlaylistsMapper, Playlists>
        implements PlaylistsService {


    private final UserPlaylistSongsService playlistSongsService;

    @Autowired
    public PlaylistsServiceImpl(@Lazy UserPlaylistSongsService playlistSongsService) {
        this.playlistSongsService = playlistSongsService;
    }

    @Override
    public void createDayRecommendations(Integer userId, List<SongData> dayRecommend) {
        LambdaQueryWrapper<Playlists> playlistsWrapper = new LambdaQueryWrapper<Playlists>()
                .eq(Playlists::getUserId, userId)
                .eq(Playlists::getPlaylistName, "每日推荐");
        Playlists playlists = baseMapper.selectOne(playlistsWrapper);

        if (playlists == null) {
          playlists = new Playlists();
          playlists.setPlaylistName("每日推荐");
          playlists.setUserId(userId);
          baseMapper.insert(playlists);
        }

        for (SongData songData : dayRecommend) {
            UserPlaylistSongs userPlaylistSongs = new UserPlaylistSongs();
            userPlaylistSongs.setUserId(userId);
            userPlaylistSongs.setPlaylistId(playlists.getPlaylistId());
            userPlaylistSongs.setExternalSongId(songData.getId());
            playlistSongsService.save(userPlaylistSongs);
        }
    }
}




