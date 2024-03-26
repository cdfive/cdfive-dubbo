package com.cdfive.user.api;

import com.cdfive.user.vo.admin.LoginReqVo;
import com.cdfive.user.vo.admin.LoginRespVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author cdfive
 */
@FeignClient(name = "cdfive-admin")
public interface AdminApi {

    @PostMapping("/api/v1/user/admin/login")
    LoginRespVo login(@RequestBody(required = false) LoginReqVo reqVo);
}
