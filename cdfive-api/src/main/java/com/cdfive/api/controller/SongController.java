package com.cdfive.api.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.cdfive.api.TestReqVo;
import com.cdfive.api.constant.UriConstant;
import com.cdfive.common.api.ApiResponse;
import com.cdfive.common.base.AbstractController;
import com.cdfive.common.util.FastJsonUtil;
import com.cdfive.common.util.JacksonUtil;
import com.cdfive.common.util.WebUtil;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.mp3.api.SongApi;
import com.cdfive.mp3.service.SongService;
import com.cdfive.mp3.vo.song.*;
import com.google.common.collect.Lists;
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

    @Value("${test:#{null}}")
//    @NacosValue(value = "${test}", autoRefreshed = true)
    private String test;

    @RequestMapping("/api/v1/mp3/test")
    public String test() {
//        log.debug("test={}", test);
        log.info("test={}", test);
        return test;
    }

    @RequestMapping(UriConstant.MP3_ALL)
//    public ApiResponse<FindAllSongRespVo> all() {
//    public FindAllSongRespVo all(@RequestBody(required = false) TestReqVo reqVo) {
    public ApiResponse<FindAllSongRespVo> all(@RequestBody(required = false) TestReqVo reqVo) {
        log.info("reqVo={}", FastJsonUtil.obj2Json(reqVo));
//        FindAllSongRespVo findAllSongRespVo = new FindAllSongRespVo();
//        SongListVo songListVo = new SongListVo();
//        songListVo.setId(1);
//        songListVo.setSongName("爱相随");
//        findAllSongRespVo.setD1(Lists.newArrayList(songListVo));

//            return succ(findAllSongRespVo);
//        return findAllSongRespVo;

//        FindAllSongRespVo respVo = songApi.findAllSong();
//        return succ(respVo);

        FindAllSongRespVo respVo = songApi.findAllSong();
//        log.info("all => " + JacksonUtil.objToJson(respVo));
        return ApiResponse.succ(respVo);
    }

    @RequestMapping(UriConstant.MP3_RANDOM_LIST)
//    public List<SongListVo> randomList() {
    public ApiResponse<List<SongListVo>> randomList() {
        List<SongListVo> list = songApi.findRandomSongList(20);
        return succ(list);
//        return list;
    }

    @RequestMapping(UriConstant.MP3_PLAY)
//    public ApiResponse<Integer> play(@RequestBody PlaySongReqVo reqVo) {
//    public ApiResponse<Integer> play(PlaySongReqVo reqVo) {
//    public ApiResponse<Integer> play(@RequestParam("id") Integer id) {
    public ApiResponse<Integer> play(Integer id) {
//    public Integer play(Integer id) {
        log.info("play,id={}", id);
//        Integer play = songApi.play(reqVo != null ? reqVo.getId() : null, WebUtil.getIp());
        Integer play = songApi.play(id, WebUtil.getIp());
        return succ(play);
//        return play;
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
