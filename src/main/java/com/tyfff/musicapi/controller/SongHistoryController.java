package com.tyfff.musicapi.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyfff.musicapi.domain.dto.User;
import com.tyfff.musicapi.domain.dto.UserSongHistory;
import com.tyfff.musicapi.domain.r.ResponseResult;
import com.tyfff.musicapi.domain.vo.PageVo;
import com.tyfff.musicapi.service.UserSongHistoryService;
import com.tyfff.musicapi.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/song-history")
public class SongHistoryController {

    @Autowired
    private UserSongHistoryService userSongHistoryService;

    @PostMapping("/{id}")
    public ResponseResult<UserSongHistory> create(@PathVariable String id) {
        User user = UserHolder.get();
        UserSongHistory userSongHistory = userSongHistoryService.getOne(new LambdaQueryWrapper<UserSongHistory>()
                .eq(UserSongHistory::getExternalSongId, id)
                .eq(UserSongHistory::getUserId,user.getUserId()));
        if (userSongHistory==null) {
            userSongHistory = new UserSongHistory();
            userSongHistory.setUserId(user.getUserId());
            userSongHistory.setExternalSongId(id);
            userSongHistory.setListenCount(1);
        }else {
            userSongHistory.setListenCount(userSongHistory.getListenCount()+1);
        }
        if (userSongHistoryService.saveOrUpdate(userSongHistory)) {
            return ResponseResult.ok("播放记录添加成功",userSongHistory);
        }else {
            throw new RuntimeException("播放历史添加失败");
        }

    }

    @GetMapping("/page")
    public ResponseResult<PageVo<UserSongHistory>> page(Long pageNum, Long pageSize){
        PageVo<UserSongHistory> songHistoryPageVo = userSongHistoryService.pageById(UserHolder.get().getUserId(), new Page<>(pageNum, pageSize));
        return ResponseResult.ok("播放历史分页数据",songHistoryPageVo);
    }
}
