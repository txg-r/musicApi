package com.tyfff.musicapi.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyfff.musicapi.domain.dto.SongData;
import com.tyfff.musicapi.domain.dto.UserSongHistory;
import com.tyfff.musicapi.service.UserSongHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MusicRecommendationService {

    @Autowired
    private UserSongHistoryService userSongHistoryService;

    private static final String NETEASE_API = "http://180.76.105.127:3000";

    private final RestTemplate restTemplate;

    public MusicRecommendationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public List<SongData> getDayRecommendByUserHistory(Integer userId, int maxRecommendations) {
        List<UserSongHistory> userSongHistories = userSongHistoryService.findTopSongsByUserIdInGivenDays(userId, 20, 7);

        List<String> recommendedSongIds = new ArrayList<>();

        // 按播放次数降序排序
        userSongHistories.sort((a, b) -> b.getListenCount().compareTo(a.getListenCount()));

        // 计算总播放次数
        int totalPlayCount = userSongHistories.stream().mapToInt(UserSongHistory::getListenCount).sum();

        int remainRecommendations = maxRecommendations;

        // 为每首歌曲分配推荐数量，并获取推荐
        for (UserSongHistory userSongHistory : userSongHistories) {
            String externalSongId = userSongHistory.getExternalSongId();
            int playCount = userSongHistory.getListenCount();
            int recommendationsForSong = (int) Math.ceil((double) maxRecommendations * playCount / totalPlayCount);
            if (remainRecommendations <= 0) {
                break;
            }
            if (remainRecommendations - recommendationsForSong < 0) {
                recommendationsForSong = remainRecommendations;
            }
            List<String> similarSongIds = getRecommendedSongIds(userId, externalSongId, recommendationsForSong);
            remainRecommendations -= similarSongIds.size();
            recommendedSongIds.addAll(similarSongIds);
        }

        List<SongData> dayRecommend = recommendedSongIds.stream().map(SongData::new).collect(Collectors.toList());

        if (remainRecommendations > 0) {
            dayRecommend.addAll(getRandomSongData(remainRecommendations));
        }

        return dayRecommend;
    }

    private List<String> getRecommendedSongIds(Integer userId, String externalSongId, int recommendationsForSong) {

        // 获取相似歌曲
        ResponseEntity<String> simiSongResponse = restTemplate.getForEntity(NETEASE_API + "/simi/song?id=" + externalSongId, String.class);
        List<String> similarSongIds = parseSongIdsFromResponse(simiSongResponse.getBody());
        Collections.shuffle(similarSongIds);

        // 按比例截取相似歌曲
        if (similarSongIds.size() > recommendationsForSong) {
            ArrayList<String> resultSongIds = new ArrayList<>();
            similarSongIds.stream().takeWhile(i -> resultSongIds.size() < recommendationsForSong)
                    .filter(songId -> {
                        UserSongHistory song = userSongHistoryService.getBySongId(songId, userId);
                        return song == null || song.getListenCount() == 1;
                    }).forEach(resultSongIds::add);
        }

        return new ArrayList<>(similarSongIds);
    }

    private List<String> parseSongIdsFromResponse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<String> songIds = new ArrayList<>();

        try {
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode songsNode = rootNode.get("songs");

            if (songsNode.isArray()) {
                for (JsonNode songNode : songsNode) {
                    SongData songData = objectMapper.treeToValue(songNode, SongData.class);
                    songIds.add(String.valueOf(songData.getId()));
                }
            }
        } catch (IOException e) {
            // 处理解析错误
            e.printStackTrace();
        }

        return songIds;
    }

    public SongData getOneRecommendByUserHistory(Integer userId) {
        List<UserSongHistory> top10In7Day = userSongHistoryService.findTopSongsByUserIdInGivenDays(userId, 3, 20);

       if (top10In7Day.size()==0){
           return getRandomSongData(1).get(0);
       }

        // 生成随机数，取出对应的歌曲
        Random random = new Random();
        int randomIndex = random.nextInt(top10In7Day.size());
        UserSongHistory randomSong = top10In7Day.get(randomIndex);

        //根据随机取出的歌进行推荐
        List<String> recommendedSongIds = getRecommendedSongIds(userId, randomSong.getExternalSongId(), 1);
        if (recommendedSongIds.isEmpty()) {
            return getOneRecommendByUserHistory(userId);
        }
        return new SongData(recommendedSongIds.get(0));

    }

    public List<SongData> getRandomSongData(int count) {
        List<SongData> songDataList = new ArrayList<>();

        // 获取热门歌手
        ResponseEntity<String> artistsResponse = restTemplate.getForEntity(NETEASE_API + "/top/artists?offset=0&limit=" + count, String.class);
        List<String> artistIds = parseArtistIdsFromResponse(artistsResponse.getBody());

        // 获取每个歌手的热门歌曲
        for (String artistId : artistIds) {
            ResponseEntity<String> songsResponse = restTemplate.getForEntity(NETEASE_API + "/artist/top/song?id=" + artistId, String.class);
            List<String> songIds = parseSongIdsFromResponse(songsResponse.getBody());

            // 随机选择一首歌曲
            Collections.shuffle(songIds);
            if (!songIds.isEmpty()) {
                String selectedSongId = songIds.get(0);
                songDataList.add(new SongData(selectedSongId));
            }
        }

        return songDataList;
    }

    private List<String> parseArtistIdsFromResponse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<String> artistIds = new ArrayList<>();

        try {
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode artistsNode = rootNode.get("artists");

            if (artistsNode.isArray()) {
                for (JsonNode artistNode : artistsNode) {
                    String artistId = artistNode.get("id").asText();
                    artistIds.add(artistId);
                }
            }
        } catch (IOException e) {
            // 处理解析错误
            e.printStackTrace();
        }

        return artistIds;
    }


}
