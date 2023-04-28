package com.cdfive.framework.message.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cdfive
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LogRequestMessageVo implements Serializable {

    private static final long serialVersionUID = 7348233045069030483L;

    private String traceId;

    private String appName;

    private String appIp;

    private Integer appPort;

    private String requestUri;

    private String remoteAddr;

    private Long costMs;

    private String requestBody;

    private Boolean exExist;

    private String exClassName;

    private String exStackTrace;

    private Date startTime;

    private Date createTime;
}
