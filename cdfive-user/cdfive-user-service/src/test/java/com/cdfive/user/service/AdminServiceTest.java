package com.cdfive.user.service;

import com.cdfive.common.util.JacksonUtil;
import com.cdfive.common.vo.page.BootstrapPageRespVo;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.user.BaseTest;
import com.cdfive.user.vo.admin.*;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author cdfive
 */
public class AdminServiceTest extends BaseTest {

    @Autowired
    private AdminService adminService;

    @Test
    public void testFindAdminByUsername() {
        String username = "cdfive";
        FindAdminByUsernameRespVo respVo = adminService.findAdminByUsername(username);
        System.out.println(JacksonUtil.objToJson(respVo));
    }

    @Test
    public void testQueryAdminListPage() {
        QueryAdminListPageReqVo reqVo = new QueryAdminListPageReqVo();
        PageRespVo<QueryAdminListPageRespVo> respVo = adminService.queryAdminListPage(reqVo);
        System.out.println(JacksonUtil.objToJson(respVo));
    }

    @Test
    public void testQueryAdminListBootstrapPage() {
        QueryAdminListPageReqVo reqVo = new QueryAdminListPageReqVo();
        BootstrapPageRespVo<QueryAdminListPageRespVo> respVo = adminService.queryAdminListBootstrapPage(reqVo);
        System.out.println(JacksonUtil.objToJson(respVo));
    }

    @Test
    public void testAddAdmin() {
        AddAdminReqVo reqVo = new AddAdminReqVo();
        reqVo.setUsername("cdfive");
        reqVo.setPassword("cdfive");
        reqVo.setAliasname("five");
        reqVo.setMobile("13611116666");
        reqVo.setRoleIds(Lists.newArrayList(1));
        adminService.addAdmin(reqVo);
    }

    @Test
    public void testUpdateAdmin() {
        UpdateAdminReqVo reqVo = new UpdateAdminReqVo();
        reqVo.setId(1);
        reqVo.setAliasname("five");
        reqVo.setMobile("13611116666");
        reqVo.setRoleIds(Lists.newArrayList(1));
        adminService.updateAdmin(reqVo);
    }
}
