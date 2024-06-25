package com.cdfive.demo.mybatis.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cdfive
 */
@Data
public class PageReqVo implements Serializable {

    private static final long serialVersionUID = -5163588623811780556L;

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    public PageReqVo() {

    }

    public PageReqVo(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
}
