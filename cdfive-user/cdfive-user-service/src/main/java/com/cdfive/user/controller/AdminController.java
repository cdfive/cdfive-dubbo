package com.cdfive.user.controller;

import com.cdfive.user.api.AdminApi;
import com.cdfive.user.service.AdminService;
import com.cdfive.user.vo.admin.LoginReqVo;
import com.cdfive.user.vo.admin.LoginRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cdfive
 */
@Slf4j
@RestController
public class AdminController implements AdminApi {

    @Autowired
    private AdminService adminService;

    @Override
    public LoginRespVo login(LoginReqVo reqVo) {
        return adminService.login(reqVo);
    }
}
