package com.tyfff.musicapi.job;

import com.tyfff.musicapi.service.impl.MusicRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MusicDayJob {
    @Autowired
    private MusicRecommendationService musicRecommendationService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void executeDayRecommendation(){
        musicRecommendationService.createDayRecommendations();
    }
}
