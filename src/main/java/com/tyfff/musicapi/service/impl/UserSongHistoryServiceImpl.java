package com.tyfff.musicapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyfff.musicapi.domain.dto.UserSongHistory;
import com.tyfff.musicapi.domain.vo.PageVo;
import com.tyfff.musicapi.service.UserSongHistoryService;
import com.tyfff.musicapi.mapper.UserSongHistoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author tyfff
* @description 针对表【user_song_history】的数据库操作Service实现
* @createDate 2023-03-19 12:03:37
*/
@Service
public class UserSongHistoryServiceImpl extends ServiceImpl<UserSongHistoryMapper, UserSongHistory>
    implements UserSongHistoryService{


    @Override
    public PageVo<UserSongHistory> pageById(Integer userId, Page<UserSongHistory> userSongHistoryPage) {
        LambdaQueryWrapper<UserSongHistory> wrapper = new LambdaQueryWrapper<UserSongHistory>()
                .eq(UserSongHistory::getUserId, userId);
        Page<UserSongHistory> page = page(userSongHistoryPage, wrapper);
        PageVo<UserSongHistory> pageVo = new PageVo<>();
        pageVo.copyFromPage(page);
        return pageVo;
    }

    public List<UserSongHistory> findTopSongsByUserIdInGivenDays(Integer userId, int days, int limit) {
        return baseMapper.findTopSongsByUserIdInGivenDays(userId, days, limit);
    }

    @Override
    public UserSongHistory getBySongId(String songId,Integer userId) {
        LambdaQueryWrapper<UserSongHistory> wrapper = new LambdaQueryWrapper<UserSongHistory>()
                .eq(UserSongHistory::getUserId, userId)
                .eq(UserSongHistory::getExternalSongId, songId);
        return baseMapper.selectOne(wrapper);
    }

}




