package com.cdfive.mp3.transformer;

import com.cdfive.mp3.entity.po.SongPo;
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
    public QuerySongListPageRespVo apply(SongPo po) {
        if (po == null) {
            return null;
        }

        QuerySongListPageRespVo vo = new QuerySongListPageRespVo();
        vo.setId(po.getId());
        vo.setName(po.getSongName());
        vo.setAuthor(po.getAuthor());
        vo.setPath(po.getPath());
        vo.setDigit(po.getDigit());
        vo.setReason(po.getReason());
        vo.setPlayCount(po.getPlayCount());
        vo.setCreateTime(po.getCreateTime());
        vo.setUpdateTime(po.getUpdateTime());
        return vo;
    }
}
