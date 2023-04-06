package com.tyfff.musicapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyfff.musicapi.domain.dto.User;
import com.tyfff.musicapi.domain.dto.UserPlaylistSongs;
import com.tyfff.musicapi.domain.r.ResponseResult;
import com.tyfff.musicapi.domain.vo.PageVo;
import com.tyfff.musicapi.service.UserPlaylistSongsService;
import com.tyfff.musicapi.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/playlist-song")
public class PlaylistSongController {

    @Autowired
    private UserPlaylistSongsService userPlaylistSongsService;


    @PostMapping("/add-favorite")
    public ResponseResult<Void> addFavorite(@RequestParam String songId){
        User user = UserHolder.get();
        return userPlaylistSongsService.createFavoriteSong(user.getUserId(),songId);
    }

    @PostMapping("/del-favorite")
    public ResponseResult<Void> delFavorite(@RequestParam String songId){
        User user = UserHolder.get();
        return userPlaylistSongsService.removeFavoriteSong(user.getUserId(),songId);
    }

    @GetMapping("/page-favorite")
    public ResponseResult<PageVo<UserPlaylistSongs>> pageFavorite(Long pageNum, Long pageSize){
        PageVo<UserPlaylistSongs> userPlaylistSongsPageVo = userPlaylistSongsService
                .pageFavoriteByUserId(UserHolder.get().getUserId(), new Page<UserPlaylistSongs>(pageNum, pageSize));
        return ResponseResult.ok("收藏歌曲分页",userPlaylistSongsPageVo);
    }

}
