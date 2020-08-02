package com.cdfive.log.po;

import com.cdfive.support.jpa.po.BasePo;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author cdfive
 */
@Data
@Entity
@Table(name = "cdfive_method_log")
public class MethodLogPo extends BasePo<Integer> {

    private String ip;

    private String methodName;

    @Column(columnDefinition = "text")
    private String requestJson;

    @Column(columnDefinition = "text")
    private String responseJson;

    private Boolean success;

    private String exceptionMessage;

    @Column(columnDefinition = "text")
    private String exceptionStackTrace;

    private Date startTime;

    private Date endTime;

    private Long timeCostMs;
}
