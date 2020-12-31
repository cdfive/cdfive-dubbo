package com.cdfive.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author cdfive
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class JsonResult<T> implements Serializable {

    public static final Integer SUCCESS_CODE = 200;

    private Integer code;

    private String msg;

    private T data;

    private Long ts = System.currentTimeMillis();
}
