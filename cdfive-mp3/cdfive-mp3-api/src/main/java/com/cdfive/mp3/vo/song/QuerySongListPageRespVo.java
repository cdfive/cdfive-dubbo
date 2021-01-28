package com.cdfive.mp3.vo.song;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private Integer playCount;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
