package com.cdfive.api.controller;

import com.cdfive.api.constant.UriConstant;
import com.cdfive.common.api.ApiResponse;
import com.cdfive.common.base.AbstractController;
import com.cdfive.common.util.WebUtil;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.mp3.service.SongService;
import com.cdfive.mp3.vo.song.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
        FindAllSongRespVo respVo = null;
        try {
            respVo = songService.findAllSong();
        } catch (Exception e) {
            return ApiResponse.errBizMsg("findAllSong error," + e.getMessage());
        }
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

    @RequestMapping(UriConstant.MP3_LIST)
    public ApiResponse<PageRespVo<QuerySongListPageRespVo>> list(QuerySongListPageReqVo reqVo) {
        PageRespVo<QuerySongListPageRespVo> respVo = songService.querySongListPage(reqVo);
        return succ(respVo);
    }

    @PostMapping(UriConstant.MP3_ADD)
    public ApiResponse<Integer> add(AddSongReqVo reqVo) {
        Integer result = songService.addSong(reqVo);
        return succ(result);
    }

    @PostMapping(UriConstant.MP3_UPDATE)
    public ApiResponse<Integer> update(UpdateSongReqVo reqVo) {
        songService.updateSong(reqVo);
        return succ();
    }

    @PostMapping(UriConstant.MP3_DEL)
    public ApiResponse<Integer> del(List<Integer> ids) {
        songService.deleteSong(ids);
        return succ();
    }
}
