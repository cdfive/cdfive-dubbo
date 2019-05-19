package com.cdfive.mp3.service;

import com.alibaba.fastjson.JSON;
import com.cdfive.mp3.BaseTest;
import com.cdfive.mp3.vo.song.FindAllSongRespVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author cdfive
 */
public class SongServiceTest extends BaseTest {

    @Autowired
    private SongService songService;

    @Test
    public void testFindAllSong() {
        FindAllSongRespVo respVo = songService.findAllSong();
        System.out.println(JSON.toJSONString(respVo));
    }

    @Test
    public void testPlaySong() {
        Integer playCount = songService.play(1, "127.0.0.1");
        System.out.println(playCount);
    }
}
