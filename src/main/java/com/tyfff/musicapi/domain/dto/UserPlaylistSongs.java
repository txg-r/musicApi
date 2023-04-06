package com.tyfff.musicapi.domain.dto;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user_playlist_songs
 */
@TableName(value ="user_playlist_songs")
@Data
public class UserPlaylistSongs implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer userId;

    /**
     * 
     */
    private Integer playlistId;

    /**
     * 
     */
    private String externalSongId;

    /**
     * 
     */
    @TableField(fill = FieldFill.INSERT)
    private Date addedAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}