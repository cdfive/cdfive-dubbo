package com.cdfive.mp3.service;

import com.cdfive.common.vo.page.BootstrapPageRespVo;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.mp3.vo.song.*;

import java.util.List;

/**
 * @author cdfive
 */
public interface SongService {

    FindAllSongRespVo findAllSong();

    List<SongListVo> findRandomSongList(Integer num);

    Integer play(Integer id, String ip);

    PageRespVo<QuerySongListPageRespVo> querySongListPage(QuerySongListPageReqVo reqVo);

    BootstrapPageRespVo<QuerySongListPageRespVo> querySongListBootstrapPage(QuerySongListPageReqVo reqVo);

    FindSongDetailVo findSongDetail(Integer id);

    QuerySongDetailRespVo querySongDetail(QuerySongDetailReqVo reqVo);

    Integer addSong(AddSongReqVo reqVo);

    void updateSong(UpdateSongReqVo reqVo);

    void deleteSong(List<Integer> ids);
}
