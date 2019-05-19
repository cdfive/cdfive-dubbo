package com.cdfive.web.controller;

import com.cdfive.common.api.ApiResponse;
import com.cdfive.common.base.AbstractController;
import com.cdfive.common.util.WebUtil;
import com.cdfive.mp3.service.SongService;
import com.cdfive.mp3.vo.song.FindAllSongRespVo;
import com.cdfive.mp3.vo.song.SongListVo;
import com.cdfive.web.constant.UriConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author cdfive
 */
@RestController
public class SongController extends AbstractController {

    @Autowired
    private SongService songService;

    @RequestMapping(UriConstant.MP3_ALL)
    public ApiResponse<FindAllSongRespVo> all() {
        FindAllSongRespVo respVo = songService.findAllSong();
        return succ(respVo);
    }

    @RequestMapping(UriConstant.MP3_RANDOM_LIST)
    public ApiResponse<List<SongListVo>> randomList() {
        List<SongListVo> list = songService.findRandomSongList(20);
        return succ(list);
    }

    @RequestMapping(UriConstant.MP3_PLAY)
    public ApiResponse<Integer> play(Integer id) {
        Integer play = songService.play(id, WebUtil.getIp());
        return succ(play);
    }
}
