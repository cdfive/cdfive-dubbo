package com.cdfive.mp3.controller;

import com.cdfive.common.exception.ServiceException;
import com.cdfive.common.util.JacksonUtil;
import com.cdfive.common.util.StringUtil;
import com.cdfive.common.util.WebUtil;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.mp3.api.SongApi;
import com.cdfive.mp3.service.SongService;
import com.cdfive.mp3.vo.song.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @ResponseBody
    @RequestMapping("/api/v1/mp3/test")
//    public String test() {
//    public Object test() {
    public void test() {
//        int i = 1 / 0;

        log.info("test success");
//        return "test success";
    }

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
        if (StringUtil.isBlank(ip)) {
            ip = WebUtil.getIp();
        }
        Integer respVo = songService.play(id, ip);
        return respVo;
    }

    @Override
    public PageRespVo<QuerySongListPageRespVo> querySongListPage(QuerySongListPageReqVo reqVo) {
        return songService.querySongListPage(reqVo);
    }

    @Override
    public QuerySongDetailRespVo querySongDetail(QuerySongDetailReqVo reqVo) {
        return songService.querySongDetail(reqVo);
    }

    @Override
    public Integer addSong(AddSongReqVo reqVo) {
        return songService.addSong(reqVo);
    }

    @Override
    public void updateSong(UpdateSongReqVo reqVo) {
        songService.updateSong(reqVo);
    }

    @Override
    public void deleteSong(DeleteSongReqVo reqVo) {
        songService.deleteSong(reqVo);
    }
}
