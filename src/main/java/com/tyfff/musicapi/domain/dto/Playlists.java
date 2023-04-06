package com.tyfff.musicapi.domain.dto;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName playlists
 */
@TableName(value ="playlists")
@Data
public class Playlists implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer playlistId;

    /**
     * 
     */
    private Integer userId;

    /**
     * 
     */
    private String playlistName;

    /**
     * 
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}