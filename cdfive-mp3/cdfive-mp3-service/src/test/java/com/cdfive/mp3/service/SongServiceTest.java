package com.cdfive.mp3.service;

import com.alibaba.fastjson.JSON;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.mp3.BaseTest;
import com.cdfive.mp3.vo.song.FindAllSongRespVo;
import com.cdfive.mp3.vo.song.QuerySongListPageReqVo;
import com.cdfive.mp3.vo.song.QuerySongListPageRespVo;
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

    @Test
    public void testQuerySongListPage() {
        QuerySongListPageReqVo reqVo = new QuerySongListPageReqVo();
        PageRespVo<QuerySongListPageRespVo> respVo = songService.querySongListPage(reqVo);
        System.out.println(JSON.toJSONString(respVo));
    }
}
