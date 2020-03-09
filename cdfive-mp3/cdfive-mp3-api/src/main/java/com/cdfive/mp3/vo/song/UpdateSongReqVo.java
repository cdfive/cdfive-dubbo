package com.cdfive.mp3.vo.song;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author cdfive
 */
@Data
public class UpdateSongReqVo implements Serializable {

    private static final long serialVersionUID = 8706541111128763855L;

    private List<Integer> categoryIds;

    private Integer id;

    private String name;

    private String author;

    private String description;

    private String path;

    private String reason;

    private Integer sort;
}
