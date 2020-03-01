package com.cdfive.common.vo.page;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cdfive
 */
@Data
public class PageReqVo implements Serializable {

    private Integer pageNum = 1;

    private Integer pageSize = 10;
}
