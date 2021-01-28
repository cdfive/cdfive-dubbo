package com.cdfive.user.service;

import com.cdfive.common.util.JacksonUtil;
import com.cdfive.common.vo.page.BootstrapPageRespVo;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.user.BaseTest;
import com.cdfive.user.vo.menu.AddMenuReqVo;
import com.cdfive.user.vo.menu.QueryMenuListPageReqVo;
import com.cdfive.user.vo.menu.QueryMenuListPageRespVo;
import com.cdfive.user.vo.menu.UpdateMenuReqVo;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author cdfive
 */
public class MenuServiceTest extends BaseTest {

    @Autowired
    private MenuService menuService;

    @Test
    public void testQueryMenuListPage() {
        QueryMenuListPageReqVo reqVo = new QueryMenuListPageReqVo();
        PageRespVo<QueryMenuListPageRespVo> respVo = menuService.queryMenuListPage(reqVo);
        System.out.println(JacksonUtil.objToJson(respVo));
    }

    @Test
    public void testQueryMenuListBootstrapPage() {
        QueryMenuListPageReqVo reqVo = new QueryMenuListPageReqVo();
        BootstrapPageRespVo<QueryMenuListPageRespVo> respVo = menuService.queryMenuListBootstrapPage(reqVo);
        System.out.println(JacksonUtil.objToJson(respVo));
    }

    @Test
    public void testAddMenu() {
        AddMenuReqVo reqVo;
        Integer newId;
        Integer parentId;

        reqVo = new AddMenuReqVo();
        reqVo.setName("mp3管理");
        newId = menuService.addMenu(reqVo);
        System.out.println(newId);
        parentId = newId;

        reqVo = new AddMenuReqVo();
        reqVo.setParentId(parentId);
        reqVo.setName("分类列表");
        reqVo.setUrl("javascript:menuopen('/category/list')");
        newId = menuService.addMenu(reqVo);
        System.out.println(newId);

        reqVo = new AddMenuReqVo();
        reqVo.setParentId(parentId);
        reqVo.setName("mp3列表");
        reqVo.setUrl("javascript:menuopen('/mp3/list')");
        newId = menuService.addMenu(reqVo);
        System.out.println(newId);
    }

    @Test
    public void testUpdateMenu() {
        UpdateMenuReqVo reqVo;
    }

    @Test
    public void testDeleteMenu() {
        menuService.deleteMenu(Lists.newArrayList(1, 2));
        System.out.println("testDeleteMenu done");
    }
}
