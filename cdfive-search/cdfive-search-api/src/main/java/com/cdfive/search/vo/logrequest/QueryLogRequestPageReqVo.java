package com.cdfive.search.vo.logrequest;

import com.cdfive.common.vo.page.PageReqVo;
import lombok.Data;

/**
 * @author cdfive
 */
@Data
public class QueryLogRequestPageReqVo extends PageReqVo {

    private static final long serialVersionUID = -1923796723872136058L;

    private String id;

    private String traceId;

    private String appName;

    private String ip;

    private Integer port;

    private String requestUri;

    private Boolean success;

    private String sortField;

    private String sortOrder;
}
