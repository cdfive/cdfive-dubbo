package com.cdfive.mp3.api;

import com.cdfive.mp3.vo.song.FindAllSongRespVo;
import com.cdfive.mp3.vo.song.SongListVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author cdfive
 */
@FeignClient(name = "cdfive-mp3")
public interface SongFeign {

    @RequestMapping("/mp3/all")
    FindAllSongRespVo findAllSong();

    @RequestMapping("/mp3/random_list")
    List<SongListVo> findRandomSongList(@RequestParam("num") Integer num);

    @RequestMapping("/play")
    Integer play(@RequestParam("id") Integer id, @RequestParam("ip") String ip);
}
