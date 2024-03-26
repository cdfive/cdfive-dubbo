package com.cdfive.mp3.controller;

import com.cdfive.common.exception.ServiceException;
import com.cdfive.common.util.JacksonUtil;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.mp3.api.SongApi;
import com.cdfive.mp3.service.SongService;
import com.cdfive.mp3.vo.song.FindAllSongRespVo;
import com.cdfive.mp3.vo.song.QuerySongListPageReqVo;
import com.cdfive.mp3.vo.song.QuerySongListPageRespVo;
import com.cdfive.mp3.vo.song.SongListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author cdfive
 */
@Slf4j
@RestController
public class SongController implements SongApi {

    @Autowired
    private SongService songService;

    @Override
    public FindAllSongRespVo findAllSong() {
//        if (true) throw new RuntimeException("debug");
//        if (true) throw new ServiceException("debug", 100100);
//        if (true) throw new ServiceException("debug", 500);
//        if (true) throw new RuntimeException("debug");
        FindAllSongRespVo respVo = songService.findAllSong();

//        log.info("findAllSong => " + JacksonUtil.objToJson(respVo));
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

    @Override
    public PageRespVo<QuerySongListPageRespVo> querySongListPage(QuerySongListPageReqVo reqVo) {
        return songService.querySongListPage(reqVo);
    }
}
