package com.tyfff.musicapi.controller;

import com.tyfff.musicapi.service.UserPlaylistSongsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/playlsit")
public class PlaylistController {

    @Autowired
    private UserPlaylistSongsService userPlaylistSongsService;


}
