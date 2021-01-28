package com.cdfive.user.service;

import com.cdfive.common.vo.page.BootstrapPageRespVo;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.user.vo.admin.*;

import java.util.List;

/**
 * @author cdfive
 */
public interface AdminService {

    FindAdminByUsernameRespVo findAdminByUsername(String username);

    PageRespVo<QueryAdminListPageRespVo> queryAdminListPage(QueryAdminListPageReqVo reqVo);

    BootstrapPageRespVo<QueryAdminListPageRespVo> queryAdminListBootstrapPage(QueryAdminListPageReqVo reqVo);

    FindAdminDetailVo findAdminDetail(Integer id);

    Integer addAdmin(AddAdminReqVo reqVo);

    void updateAdmin(UpdateAdminReqVo reqVo);

    void deleteAdmin(List<Integer> ids);

    void modifyAdminPassword(ModifyAdminPasswordReqVo reqVo);
}
