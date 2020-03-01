package com.cdfive.mp3.transformer;

import com.cdfive.mp3.po.SongPo;
import com.cdfive.mp3.vo.song.QuerySongListPageRespVo;

import java.util.function.Function;

/**
 * @author cdfive
 */
public class QuerySongListPageTransformer implements Function<SongPo, QuerySongListPageRespVo> {

    public static QuerySongListPageTransformer INSTANCE = new QuerySongListPageTransformer();

    private QuerySongListPageTransformer() {

    }

    @Override
    public QuerySongListPageRespVo apply(SongPo songPo) {
        if (songPo == null) {
            return null;
        }

        QuerySongListPageRespVo vo = new QuerySongListPageRespVo();
        vo.setId(songPo.getId());
        vo.setName(songPo.getSongName());
        vo.setAuthor(songPo.getAuthor());
        vo.setPath(songPo.getPath());
        vo.setDigit(songPo.getDigit());
        vo.setReason(songPo.getReason());
        vo.setCreateTime(songPo.getCreateTime());
        vo.setUpdateTime(songPo.getUpdateTime());
        return vo;
    }
}
