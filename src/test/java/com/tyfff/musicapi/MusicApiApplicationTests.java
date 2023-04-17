package com.tyfff.musicapi;

import com.tyfff.musicapi.domain.dto.SongData;
import com.tyfff.musicapi.service.UserService;
import com.tyfff.musicapi.service.impl.MusicRecommendationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MusicApiApplicationTests {
    @Autowired
    private MusicRecommendationService musicRecommendationService;

    @Autowired
    private UserService userService;

    @Test
    public void recommendTest(){
        userService.authenticateUser("tyf","123456");

        List<SongData> dayRecommend = musicRecommendationService.getDayRecommend(2);

        dayRecommend.forEach(System.out::println);
    }

}
