package com.cdfive.user.service;

import com.cdfive.common.vo.page.BootstrapPageRespVo;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.user.vo.role.*;

import java.util.List;

/**
 * @author cdfive
 */
public interface RoleService {

    PageRespVo<QueryRoleListPageRespVo> queryRoleListPage(QueryRoleListPageReqVo reqVo);

    BootstrapPageRespVo<QueryRoleListPageRespVo> queryRoleListBootstrapPage(QueryRoleListPageReqVo reqVo);

    FindRoleDetailVo findRoleDetail(Integer id);

    Integer addRole(AddRoleReqVo reqVo);

    void updateRole(UpdateRoleReqVo reqVo);

    void deleteRole(List<Integer> ids);

    void addRoleMenu(AddRoleMenuReqVo roleMenuReqVo);
}
