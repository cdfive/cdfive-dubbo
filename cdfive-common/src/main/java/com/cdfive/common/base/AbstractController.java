package com.cdfive.common.base;

import com.cdfive.common.api.ApiResponse;

/**
 * @author cdfive
 */
public class AbstractController {

    protected <T> ApiResponse<T> succ() {
        return ApiResponse.succ();
    }

    protected <T> ApiResponse<T> succ(T data) {
        return ApiResponse.succ(data);
    }
}
