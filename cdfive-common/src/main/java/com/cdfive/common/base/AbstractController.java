package com.cdfive.common.base;

import com.cdfive.common.api.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cdfive
 */
public class AbstractController {

    protected Logger log = LoggerFactory.getLogger(getClass());

    protected <T> ApiResponse<T> succ() {
        return ApiResponse.succ();
    }

    protected <T> ApiResponse<T> succ(T data) {
        return ApiResponse.succ(data);
    }
}
