package com.cdfive.search.vo.methodlog;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cdfive
 */
@Data
public class SaveMethodLogReqVo implements Serializable {

    private Integer id;

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

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;
}
