package com.tyfff.musicapi.domain.dto;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user_song_history
 */
@TableName(value ="user_song_history")
@Data
public class UserSongHistory implements Serializable {
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
    private String externalSongId;

    /**
     * 
     */
    private Integer listenCount;

    /**
     * 
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date lastListenedAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}