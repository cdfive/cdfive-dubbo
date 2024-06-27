package com.cdfive.demo.mybatis.base;

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
public class JsonResult<T> implements Serializable {

    private static final long serialVersionUID = -1381967828290529922L;

    private static final Integer CODE_SUCCESS = 200;

    private static final Integer CODE_ERROR = 500;

    private static final String MSG_SUCCESS = "success";

    public static <T> JsonResult<T> success(T data) {
        JsonResult<T> result = new JsonResult<T>();
        result.setCode(CODE_SUCCESS);
        result.setMsg(MSG_SUCCESS);
        result.setData(data);
        return result;
    }

    public static <T> JsonResult<T> error(String msg) {
        JsonResult<T> result = new JsonResult<T>();
        result.setCode(CODE_ERROR);
        result.setMsg(msg);
        return result;
    }

    private Integer code;

    private String msg;

    private T data;
}
