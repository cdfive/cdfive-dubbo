package com.cdfive.search.vo.bizlog;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cdfive
 */
@Data
public class SaveBizLogReqVo implements Serializable {

    private Integer id;

    private String info;

    private Integer keyId;

    private String ip;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;
}
