package com.cdfive.mp3.repository.db;

import com.cdfive.mp3.vo.song.SongListVo;

import java.util.List;

/**
 * @author cdfive
 */
public interface SongRepositoryCustom {

    List<SongListVo> findSongListByCategoryName(String categoryName);

    List<SongListVo> findSongListByCategoryId(Integer categoryId);

    List<SongListVo> findSongListByDigit(Integer digit);

    List<SongListVo> findRandomSongList(Integer num);
}
