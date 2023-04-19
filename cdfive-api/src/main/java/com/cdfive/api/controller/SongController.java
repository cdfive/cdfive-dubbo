package com.cdfive.api.controller;

import com.cdfive.api.constant.UriConstant;
import com.cdfive.common.api.ApiResponse;
import com.cdfive.common.base.AbstractController;
import com.cdfive.common.util.WebUtil;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.mp3.api.SongApi;
import com.cdfive.mp3.service.SongService;
import com.cdfive.mp3.vo.song.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author cdfive
 */
@RefreshScope
@RestController
public class SongController extends AbstractController {

    @Autowired
    private SongService songService;

    @Autowired
    private SongApi songApi;

    @Value("${test:}")
    private String test;

    @RequestMapping("/test")
    public String test() {
        return test;
    }

    @RequestMapping(UriConstant.MP3_ALL)
    public ApiResponse<FindAllSongRespVo> all() {
        FindAllSongRespVo respVo = songApi.findAllSong();
        return succ(respVo);
    }

    @RequestMapping(UriConstant.MP3_RANDOM_LIST)
    public ApiResponse<List<SongListVo>> randomList() {
        List<SongListVo> list = songApi.findRandomSongList(20);
        return succ(list);
    }

    @RequestMapping(UriConstant.MP3_PLAY)
//    public ApiResponse<Integer> play(@RequestBody PlaySongReqVo reqVo) {
//    public ApiResponse<Integer> play(PlaySongReqVo reqVo) {
//    public ApiResponse<Integer> play(@RequestParam("id") Integer id) {
    public ApiResponse<Integer> play(Integer id) {
//        log.info("play,id={}", id);
//        Integer play = songApi.play(reqVo != null ? reqVo.getId() : null, WebUtil.getIp());
        Integer play = songApi.play(id, WebUtil.getIp());
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
