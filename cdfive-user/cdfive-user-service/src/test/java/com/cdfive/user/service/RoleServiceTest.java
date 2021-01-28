package com.cdfive.user.service;

import com.cdfive.common.util.JacksonUtil;
import com.cdfive.common.vo.page.BootstrapPageRespVo;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.user.BaseTest;
import com.cdfive.user.vo.role.*;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author cdfive
 */
public class RoleServiceTest extends BaseTest {

    @Autowired
    private RoleService roleService;

    @Test
    public void testQueryRoleListPage() {
        QueryRoleListPageReqVo reqVo = new QueryRoleListPageReqVo();
        PageRespVo<QueryRoleListPageRespVo> respVo = roleService.queryRoleListPage(reqVo);
        System.out.println(JacksonUtil.objToJson(respVo));
    }

    @Test
    public void testQueryRoleListBootstrapPage() {
        QueryRoleListPageReqVo reqVo = new QueryRoleListPageReqVo();
        BootstrapPageRespVo<QueryRoleListPageRespVo> respVo = roleService.queryRoleListBootstrapPage(reqVo);
        System.out.println(JacksonUtil.objToJson(respVo));
    }

    @Test
    public void testFindRoleDetail() {
        Integer id = 1;
        FindRoleDetailVo respVo = roleService.findRoleDetail(id);
        System.out.println(JacksonUtil.objToJson(respVo));
    }

    @Test
    public void testAddRole() {
        AddRoleReqVo roleReqVo = new AddRoleReqVo();
        roleReqVo.setName("管理员");
        roleReqVo.setDescription("系统管理员");
        roleReqVo.setEnable(true);
        roleService.addRole(roleReqVo);
    }

    @Test
    public void testAddRoleMenu() {
        AddRoleMenuReqVo reqVo = new AddRoleMenuReqVo();
        Integer roleId = 1;
        reqVo.setRoleId(roleId);
        List<Integer> menuIds = Lists.newArrayList(1, 2, 3);
        reqVo.setMenuIds(menuIds);
        roleService.addRoleMenu(reqVo);
        System.out.println("testAddRoleMenu done");
    }
}
