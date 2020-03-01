package com.cdfive.mp3.vo.song;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cdfive
 */
@Data
public class QuerySongListPageRespVo implements Serializable {

    private static final long serialVersionUID = 653484118285004863L;

    private Integer id;

    private String name;

    private String author;

    private String path;

    private Integer digit;

    private String reason;

    private Date createTime;

    private Date updateTime;
}
