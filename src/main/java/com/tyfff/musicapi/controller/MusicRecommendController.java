package com.tyfff.musicapi.controller;

import com.tyfff.musicapi.domain.dto.SongData;
import com.tyfff.musicapi.domain.dto.User;
import com.tyfff.musicapi.domain.r.ResponseResult;
import com.tyfff.musicapi.service.impl.MusicRecommendationService;
import com.tyfff.musicapi.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/music-recommend")
public class MusicRecommendController {
    @Autowired
    private MusicRecommendationService musicRecommendationService;

    @GetMapping
    public ResponseResult<SongData> simRecommend(){
        User user = UserHolder.get();
        SongData songData = musicRecommendationService.getOneRecommendByUserHistory(user.getUserId());
        return ResponseResult.ok("单独推荐",songData);
    }

    @GetMapping("/day-recommend")
    public ResponseResult<List<SongData>> dayRecommend(){
        User user = UserHolder.get();
        List<SongData> dayRecommend = musicRecommendationService.getDayRecommendByUserHistory(user.getUserId(), 20);
        return ResponseResult.ok("每日推荐",dayRecommend);
    }
}
