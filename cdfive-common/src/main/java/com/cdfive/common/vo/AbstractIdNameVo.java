package com.cdfive.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author cdfive
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class AbstractIdNameVo<T extends Serializable> implements Serializable {

    private Integer id;

    private String name;
}
