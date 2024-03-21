package com.cdfive.search.vo.logrequest;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cdfive
 */
@Data
public class QueryLogRequestPageRespVo implements Serializable {

    private static final long serialVersionUID = -2158277900699345906L;

    private String id;

    private String traceId;

    private String appName;

    private String appIp;

    private Integer appPort;

    private String requestUri;

    private String remoteAddr;

    private Long costMs;

    private String requestBody;

    private boolean exExist;

    private String exClassName;

    private String exStackTrace;

    private Date startTime;

    private Date createTime;

    private Date updateTime;
}
