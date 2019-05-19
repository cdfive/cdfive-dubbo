package com.cdfive.mp3.vo.song;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author cdfive
 */
@Data
public class FindAllSongRespVo implements Serializable {

    private static final long serialVersionUID = -8915234715436995632L;

    private List<SongListVo> d1;

    private List<SongListVo> d2;

    private List<SongListVo> d3;

    private List<SongListVo> d4;

    private List<SongListVo> d5;

    private List<SongListVo> dYu;

    private List<SongListVo> dLiZhi;

    private List<SongListVo> dAiQing;

    private List<SongListVo> dWenNuan;

    private List<SongListVo> dMan;

    private List<SongListVo> dEn;

    private List<SongListVo> dYy;

    private List<SongListVo> dWt;

    private List<SongListVo> dDqxh;

    private List<SongListVo> dSpec;

    private List<SongListVo> d2016;

    private List<SongListVo> d2017;

    private List<SongListVo> d2018;

    private List<SongListVo> d2019;
}
