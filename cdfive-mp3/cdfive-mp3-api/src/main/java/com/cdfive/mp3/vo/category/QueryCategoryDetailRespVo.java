package com.cdfive.mp3.vo.category;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cdfive
 */
@Data
public class QueryCategoryDetailRespVo implements Serializable {

    private static final long serialVersionUID = -528388237765309475L;

    private Integer id;

    private String name;

    private String description;

    private Integer sort;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
