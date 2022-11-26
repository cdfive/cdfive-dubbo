package com.cdfive.mp3.controller;

import com.cdfive.mp3.service.SongService;
import com.cdfive.mp3.vo.song.FindAllSongRespVo;
import com.cdfive.mp3.vo.song.SongListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author cdfive
 */
@RequestMapping("/mp3")
@RestController
public class SongController {

    @Autowired
    private SongService songService;

    @RequestMapping("/all")
    public FindAllSongRespVo findAllSong() {
        FindAllSongRespVo respVo = songService.findAllSong();
        return respVo;
    }

    @RequestMapping("/random_list")
    public List<SongListVo> findRandomSongList(Integer num) {
        List<SongListVo> respVos = songService.findRandomSongList(num);
        return respVos;
    }

    @RequestMapping("/play")
    public Integer play(Integer id, String ip) {
        Integer respVo = songService.play(id, ip);
        return respVo;
    }
}
