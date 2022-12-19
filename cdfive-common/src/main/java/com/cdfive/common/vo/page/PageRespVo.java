package com.cdfive.common.vo.page;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author cdfive
 */
@NoArgsConstructor
@Data
public class PageRespVo<Vo> implements Serializable {

    private Integer pageNum;

    private Integer pageSize;

    private Integer total;

    private Integer totalPage;

    private List<Vo> data;

    public PageRespVo(Integer pageNum, Integer pageSize, Integer total, List<Vo> data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.data = data;
        this.totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
    }
}
