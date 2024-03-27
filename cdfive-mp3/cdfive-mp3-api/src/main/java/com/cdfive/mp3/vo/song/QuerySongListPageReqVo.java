package com.cdfive.mp3.vo.song;

import com.cdfive.common.vo.page.PageReqVo;
import lombok.Data;

/**
 * @author cdfive
 */
@Data
public class QuerySongListPageReqVo extends PageReqVo {

    private static final long serialVersionUID = 1447169538199149750L;

    private String name;

    private String author;

    private Integer digit;

    private String sortField;

    private String sortOrder;
}
