package com.tyfff.musicapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyfff.musicapi.domain.dto.Playlists;
import com.tyfff.musicapi.domain.dto.UserPlaylistSongs;
import com.tyfff.musicapi.domain.r.ResponseResult;
import com.tyfff.musicapi.domain.vo.PageVo;
import com.tyfff.musicapi.service.PlaylistsService;
import com.tyfff.musicapi.service.UserPlaylistSongsService;
import com.tyfff.musicapi.mapper.UserPlaylistSongsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author tyfff
* @description 针对表【user_playlist_songs】的数据库操作Service实现
* @createDate 2023-03-19 12:03:37
*/
@Service
public class UserPlaylistSongsServiceImpl extends ServiceImpl<UserPlaylistSongsMapper, UserPlaylistSongs>
    implements UserPlaylistSongsService{

    @Autowired
    private PlaylistsService playlistsService;


    @Override
    public ResponseResult<Void> createFavoriteSong(Integer userId, String songId) {

        //用户如果第一次添加，新建favorite歌单
        Playlists favoritePlaylist = playlistsService.getOne(new LambdaQueryWrapper<Playlists>()
                .eq(Playlists::getUserId, userId)
                .eq(Playlists::getPlaylistName, "favorite"));
        if (favoritePlaylist==null){
            favoritePlaylist = new Playlists();
            favoritePlaylist.setUserId(userId);
            favoritePlaylist.setPlaylistName("favorite");
            playlistsService.save(favoritePlaylist);
        }


        UserPlaylistSongs userPlaylistSongs = new UserPlaylistSongs();
        userPlaylistSongs.setPlaylistId(favoritePlaylist.getPlaylistId());
        userPlaylistSongs.setUserId(userId);
        userPlaylistSongs.setExternalSongId(songId);
        this.save(userPlaylistSongs);
        return ResponseResult.ok("收藏成功");
    }

    @Override
    public ResponseResult<Void> removeFavoriteSong(Integer userId, String songId) {
        LambdaQueryWrapper<UserPlaylistSongs> wrapper = new LambdaQueryWrapper<UserPlaylistSongs>()
                .eq(UserPlaylistSongs::getUserId, userId)
                .eq(UserPlaylistSongs::getExternalSongId, songId);
        baseMapper.delete(wrapper);
        return ResponseResult.ok("s删除收藏成功");
    }

    @Override
    public PageVo<UserPlaylistSongs> pageFavoriteByUserId(Integer userId, Page<UserPlaylistSongs> userPlaylistSongsPage) {
        LambdaQueryWrapper<UserPlaylistSongs> wrapper = new LambdaQueryWrapper<UserPlaylistSongs>()
                .eq(UserPlaylistSongs::getUserId, userId);
        Page<UserPlaylistSongs> selectPage = baseMapper.selectPage(userPlaylistSongsPage, wrapper);
        PageVo<UserPlaylistSongs> userPlaylistSongsPageVo = new PageVo<>();
        userPlaylistSongsPageVo.copyFromPage(selectPage);
        return userPlaylistSongsPageVo;
    }
}




