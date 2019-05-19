package com.cdfive.common.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author cdfive
 */
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractPo<T extends Serializable> implements Serializable {

    @Id
    @GeneratedValue
    private T id;

    @Column(name = "create_time", nullable = false, updatable = false)
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    @Column(name = "is_deleted", nullable = false)
    private Boolean deleted;
}