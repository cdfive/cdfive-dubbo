package com.cdfive.search.vo.bizlog;

import com.cdfive.common.vo.page.PageReqVo;
import lombok.Data;

/**
 * @author cdfive
 */
@Data
public class QueryBizLogPageReqVo extends PageReqVo {

    private Integer id;

    private String info;

    private Integer keyId;
}
