package com.cdfive.mp3.service;

import com.cdfive.mp3.vo.song.AddSongReqVo;
import com.cdfive.mp3.vo.song.FindAllSongRespVo;
import com.cdfive.mp3.vo.song.SongListVo;
import com.cdfive.mp3.vo.song.UpdateSongReqVo;

import java.util.List;

/**
 * @author cdfive
 */
public interface SongService {

    FindAllSongRespVo findAllSong();

    List<SongListVo> findRandomSongList(Integer num);

    Integer play(Integer id, String ip);

    Integer addSong(AddSongReqVo reqVo);

    void updateSong(UpdateSongReqVo reqVo);

    void deleteSong(List<Integer> ids);
}
