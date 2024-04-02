package com.cdfive.mp3.vo.song;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author cdfive
 */
@Data
public class QuerySongDetailRespVo implements Serializable {

    private static final long serialVersionUID = -528388237765309475L;

    private List<Integer> categoryIds;

    private Integer id;

    private String name;

    private String author;

    private String description;

    private String path;

    private Integer digit;

    private String reason;

    private Integer playCount;

    private Integer sort;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
