package com.cdfive.mp3.service.impl;

import com.cdfive.log.service.BizLogService;
import com.cdfive.mp3.po.SongPo;
import com.cdfive.mp3.repository.SongRepository;
import com.cdfive.mp3.service.AbstractMp3Service;
import com.cdfive.mp3.service.SongService;
import com.cdfive.mp3.vo.song.AddSongReqVo;
import com.cdfive.mp3.vo.song.FindAllSongRespVo;
import com.cdfive.mp3.vo.song.SongListVo;
import com.cdfive.mp3.vo.song.UpdateSongReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cdfive
 */
@Service("songService")
public class SongServiceImpl extends AbstractMp3Service implements SongService {

    @Autowired
    private BizLogService bizLogService;

    @Autowired
    private SongRepository songRepository;

    @Override
    public FindAllSongRespVo findAllSong() {
        FindAllSongRespVo respVo = new FindAllSongRespVo();

        respVo.setD2019(songRepository.findSongListByCategoryName("2019"));
        respVo.setD2018(songRepository.findSongListByCategoryName("2018"));
        respVo.setD2017(songRepository.findSongListByCategoryName("2017"));
        respVo.setD2016(songRepository.findSongListByCategoryName("2016"));

        respVo.setD1(songRepository.findSongListByDigit(1));
        respVo.setD2(songRepository.findSongListByDigit(2));
        respVo.setD3(songRepository.findSongListByDigit(3));
        respVo.setD4(songRepository.findSongListByDigit(4));
        respVo.setD5(songRepository.findSongListByDigit(5));

        respVo.setDYu(songRepository.findSongListByCategoryName("鱼"));
        respVo.setDLiZhi(songRepository.findSongListByCategoryName("励志"));
        respVo.setDAiQing(songRepository.findSongListByCategoryName("爱情"));
        respVo.setDWenNuan(songRepository.findSongListByCategoryName("温暖"));
        respVo.setDMan(songRepository.findSongListByCategoryName("Man"));

        respVo.setDEn(songRepository.findSongListByCategoryName("英语"));
        respVo.setDYy(songRepository.findSongListByCategoryName("粤语"));
        respVo.setDWt(songRepository.findSongListByCategoryName("无题"));
        respVo.setDDqxh(songRepository.findSongListByCategoryName("单曲循环"));
        respVo.setDSpec(songRepository.findSongListByCategoryName("特别"));

        return respVo;
    }

    @Override
    public List<SongListVo> findRandomSongList(Integer num) {
        check(num > 0, "数量应大于0");

        List<SongListVo> list = songRepository.findRandomSongList(num);
        return list;
    }

    @Override
    public Integer play(Integer id, String ip) {
        checkNotNull(id, "id不能为空");

        bizLogService.addBizLog("播放mp3", id, ip);

        SongPo songPo = songRepository.findOne(id);
        check(songPo != null, "记录不存在");
        check(!songPo.getDeleted(), "记录已删除");

        Integer playCount = songPo.getPlayCount();
        if (playCount == null) {
            playCount = 0;
        }
        playCount++;
        songPo.setPlayCount(playCount);
        songRepository.save(songPo);
        return playCount;
    }

    @Override
    public Integer addSong(AddSongReqVo reqVo) {
        return null;
    }

    @Override
    public void updateSong(UpdateSongReqVo reqVo) {

    }

    @Override
    public void deleteSong(List<Integer> ids) {

    }
}
