package com.cdfive.mp3.api;

import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.mp3.vo.song.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author cdfive
 */
@FeignClient(name = "cdfive-mp3")
public interface SongApi {

    @RequestMapping("/api/v1/mp3/song/all")
    FindAllSongRespVo findAllSong();

    @RequestMapping("/api/v1/mp3/song/random_list")
    List<SongListVo> findRandomSongList(@RequestParam("num") Integer num);

    @RequestMapping("/api/v1/mp3/song/play")
    Integer play(@RequestParam("id") Integer id, @RequestParam("ip") String ip);

    @RequestMapping("/api/v1/mp3/song/list")
    PageRespVo<QuerySongListPageRespVo> querySongListPage(@RequestBody(required = false) QuerySongListPageReqVo reqVo);

    @RequestMapping("/api/v1/mp3/song/add")
    Integer addSong(@RequestBody(required = false) AddSongReqVo reqVo);
}
