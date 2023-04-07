package com.cdfive.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xiejihan
 * @date 2023-04-07
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppRestApiContextVo implements Serializable {

    private static final long serialVersionUID = 2759547270469554188L;

    private String traceId;

    private String appName;

    private Integer serverPort;

    private String requestUri;

    private String remoteAddr;

    private Long costMs;

    private String requestBody;

    private String exClassName;

    private String exStackTrace;
}
