package com.tyfff.musicapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyfff.musicapi.domain.dto.Playlists;
import com.tyfff.musicapi.service.PlaylistsService;
import com.tyfff.musicapi.mapper.PlaylistsMapper;
import org.springframework.stereotype.Service;

/**
* @author tyfff
* @description 针对表【playlists】的数据库操作Service实现
* @createDate 2023-03-19 12:03:37
*/
@Service
public class PlaylistsServiceImpl extends ServiceImpl<PlaylistsMapper, Playlists>
    implements PlaylistsService{

}




