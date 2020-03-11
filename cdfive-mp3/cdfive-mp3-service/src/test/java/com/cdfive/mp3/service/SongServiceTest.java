package com.cdfive.mp3.service;

import com.alibaba.fastjson.JSON;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.mp3.BaseTest;
import com.cdfive.mp3.vo.song.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void testAddSong() {
        AddSongReqVo reqVo = new AddSongReqVo();
        reqVo.setName("测试name");
        reqVo.setAuthor("测试author");
        reqVo.setDescription("测试description");
        reqVo.setPath("测试Path");
        reqVo.setReason("测试reason");
        reqVo.setSort(1);
        Integer result = songService.addSong(reqVo);
        System.out.println(result);
    }

    @Test
    public void testUpdateSong() {
        UpdateSongReqVo reqVo = new UpdateSongReqVo();
        reqVo.setId(270);
        reqVo.setName("测试name2");
        reqVo.setAuthor("测试author");
        reqVo.setDescription("测试description");
        reqVo.setPath("测试Path");
        reqVo.setReason("测试reason");
        reqVo.setSort(2);
        songService.updateSong(reqVo);
    }

    @Test
    public void testDeleteSong() {
        List<Integer> ids = new ArrayList<>();
        ids.add(270);

        songService.deleteSong(ids);
    }
}
