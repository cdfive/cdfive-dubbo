package com.cdfive.ctf.redis.ro;

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
public class BaseRo<ID extends Serializable> implements Serializable {

    private ID id;

    private Date createTime;

    private Date updateTime;
}
