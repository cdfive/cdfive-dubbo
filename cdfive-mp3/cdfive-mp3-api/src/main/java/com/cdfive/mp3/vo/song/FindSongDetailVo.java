package com.cdfive.mp3.vo.song;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cdfive
 */
@Data
public class FindSongDetailVo implements Serializable {

    private Integer categoryId;

    private Integer id;

    private String name;

    private String author;

    private String description;

    private String path;

    private Integer digit;

    private String reason;

    private Integer playCount;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
