package com.cdfive.mp3.vo.song;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author cdfive
 */
@Data
public class AddSongReqVo implements Serializable {

    private static final long serialVersionUID = 4835572643742300120L;

    private List<Integer> categoryIds;

    private String name;

    private String author;

    private String description;


    private String path;

    private String reason;

    private Integer sort;
}
