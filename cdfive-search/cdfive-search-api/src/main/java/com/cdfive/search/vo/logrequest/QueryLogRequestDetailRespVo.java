package com.cdfive.search.vo.logrequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cdfive
 */
@Data
public class QueryLogRequestDetailRespVo implements Serializable {

    private static final long serialVersionUID = 88699865629131294L;

    private String id;

    private String traceId;

    private String appName;

    private String appIp;

    private Integer appPort;

    private String requestUri;

    private String remoteAddr;

    private Long costMs;

    private String requestBody;

    private Boolean success;

    private String exceptionClassName;

    private String exceptionStackTrace;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
