package com.cdfive.log.po;

import com.cdfive.common.base.AbstractPo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author cdfive
 */
@Data
@Entity
@Table(name = "cdfive_biz_log")
public class BizLogPo extends AbstractPo<Integer> {

    private String info;

    private Integer keyId;

    private String ip;
}
