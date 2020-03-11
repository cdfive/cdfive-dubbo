package com.cdfive.log.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cdfive
 */
@Data
public class AddMethodLogVo implements Serializable {

    private String ip;

    private String methodName;

    private String requestJson;

    private String responseJson;

    private Boolean success;

    private String exceptionMessage;

    private String exceptionStackTrace;

    private Date startTime;

    private Date endTime;

    private Long timeCostMs;
}
