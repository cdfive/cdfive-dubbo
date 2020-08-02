package com.cdfive.mp3.repository;

import com.alibaba.fastjson.JSON;
import com.cdfive.mp3.Mp3Application;
import com.cdfive.mp3.repository.db.SongRepository;
import com.cdfive.mp3.vo.song.SongListVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author cdfive
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Mp3Application.class)
public class SongRepositoryTest {

    @Autowired
    private SongRepository songRepository;

    @Test
    public void testFindSongListByCategoryName() {
        List<SongListVo> list = songRepository.findSongListByCategoryName("爱情");
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void testFindSongListByCategoryId() {
        List<SongListVo> list = songRepository.findSongListByCategoryId(2);
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void testFindSongListByDigit() {
        List<SongListVo> list = songRepository.findSongListByDigit(2);
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void testFindRandomSongList() {
        List<SongListVo> list = songRepository.findRandomSongList(5);
        System.out.println(JSON.toJSONString(list));
    }
}
