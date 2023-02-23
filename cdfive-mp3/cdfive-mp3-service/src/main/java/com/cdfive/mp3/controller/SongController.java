package com.cdfive.mp3.controller;

import com.cdfive.mp3.api.SongApi;
import com.cdfive.mp3.service.SongService;
import com.cdfive.mp3.vo.song.FindAllSongRespVo;
import com.cdfive.mp3.vo.song.SongListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author cdfive
 */
@RestController
public class SongController implements SongApi {

    @Autowired
    private SongService songService;

    @Override
    public FindAllSongRespVo findAllSong() {
        FindAllSongRespVo respVo = songService.findAllSong();
        return respVo;
    }

    @Override
    public List<SongListVo> findRandomSongList(Integer num) {
        List<SongListVo> respVos = songService.findRandomSongList(num);
        return respVos;
    }

    @Override
    public Integer play(Integer id, String ip) {
        Integer respVo = songService.play(id, ip);
        return respVo;
    }
}
