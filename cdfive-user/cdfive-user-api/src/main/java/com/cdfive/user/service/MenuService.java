package com.cdfive.user.service;

import com.cdfive.common.vo.page.BootstrapPageRespVo;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.user.vo.menu.*;

import java.util.List;

/**
 * @author cdfive
 */
public interface MenuService {

    PageRespVo<QueryMenuListPageRespVo> queryMenuListPage(QueryMenuListPageReqVo reqVo);

    BootstrapPageRespVo<QueryMenuListPageRespVo> queryMenuListBootstrapPage(QueryMenuListPageReqVo reqVo);

    FindMenuDetailVo findMenuDetail(Integer id);

    Integer addMenu(AddMenuReqVo reqVo);

    void updateMenu(UpdateMenuReqVo reqVo);

    void deleteMenu(List<Integer> ids);
}
