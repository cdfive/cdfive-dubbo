package com.cdfive.mp3.vo.song;

import com.cdfive.common.vo.page.PageReqVo;
import lombok.Data;

/**
 * @author cdfive
 */
@Data
public class QuerySongListPageReqVo extends PageReqVo {

    private String name;

    private String author;

    private Integer digit;
}
