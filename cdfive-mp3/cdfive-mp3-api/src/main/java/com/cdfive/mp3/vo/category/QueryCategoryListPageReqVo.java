package com.cdfive.mp3.vo.category;

import com.cdfive.common.vo.page.PageReqVo;
import lombok.Data;

import java.io.Serializable;

/**
 * @author cdfive
 */
@Data
public class QueryCategoryListPageReqVo extends PageReqVo {

    private String name;

    private String sortField;

    private String sortOrder;
}
