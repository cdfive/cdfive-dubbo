package com.cdfive.user.service.impl;

import com.cdfive.common.util.JpaPageUtil;
import com.cdfive.common.vo.page.BootstrapPageRespVo;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.user.po.MenuPo;
import com.cdfive.user.repository.MenuRepository;
import com.cdfive.user.repository.specification.QueryMenuSpecification;
import com.cdfive.user.service.AbstractUserService;
import com.cdfive.user.service.MenuService;
import com.cdfive.user.transformer.QueryMenuListPageTransformer;
import com.cdfive.user.vo.menu.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author cdfive
 */
@Slf4j
@Service("menuService")
public class MenuServiceImpl extends AbstractUserService implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public PageRespVo<QueryMenuListPageRespVo> queryMenuListPage(QueryMenuListPageReqVo reqVo) {
        return JpaPageUtil.buildPage(reqVo, menuRepository, new QueryMenuSpecification(reqVo), QueryMenuListPageTransformer.INSTANCE);
    }

    @Override
    public BootstrapPageRespVo<QueryMenuListPageRespVo> queryMenuListBootstrapPage(QueryMenuListPageReqVo reqVo) {
        return JpaPageUtil.buildBootstrapPage(reqVo, menuRepository, new QueryMenuSpecification(reqVo), QueryMenuListPageTransformer.INSTANCE);
    }

    @Override
    public FindMenuDetailVo findMenuDetail(Integer id) {
        checkNotNull(id, "记录id不能为空");

        Optional<MenuPo> optMenuPo = menuRepository.findById(id);
        checkCondition(optMenuPo.isPresent(), "记录不存在,id=" + id);

        MenuPo menuPo = optMenuPo.get();
        FindMenuDetailVo detailVo = new FindMenuDetailVo();
        detailVo.setId(menuPo.getId());
        detailVo.setName(menuPo.getName());
        detailVo.setDescription(menuPo.getDescription());
        detailVo.setUrl(menuPo.getUrl());
        detailVo.setIcon(menuPo.getIcon());
        detailVo.setCreateTime(menuPo.getCreateTime());
        detailVo.setUpdateTime(menuPo.getUpdateTime());
        return detailVo;
    }

    @Override
    public Integer addMenu(AddMenuReqVo reqVo) {
        checkNotNull(reqVo, "请求参数不能为空");

        MenuPo menuPo = new MenuPo();

        String name = reqVo.getName();
        checkNotBlank(name, "菜单名称不能为空");
        menuPo.setName(name);

        String description = reqVo.getDescription();
        menuPo.setDescription(description);

        String url = reqVo.getUrl();
        menuPo.setUrl(url);

        String icon = reqVo.getIcon();
        menuPo.setIcon(icon);

        Integer parentId = reqVo.getParentId();
        if (parentId != null) {
            Optional<MenuPo> optParentMenuPo = menuRepository.findById(parentId);
            checkCondition(optParentMenuPo.isPresent(), "父菜单不存在,parentId=" + parentId);

            MenuPo parentMenuPo = optParentMenuPo.get();
            menuPo.setParent(parentMenuPo);
        }

        menuPo.setDeleted(false);
        menuPo.setCreateTime(now());

        menuRepository.save(menuPo);
        return menuPo.getId();
    }

    @Override
    public void updateMenu(UpdateMenuReqVo reqVo) {
        checkNotNull(reqVo, "请求参数不能为空");

        Integer id = reqVo.getId();
        checkNotNull(id, "记录id不能为空");

        Optional<MenuPo> optMenuPo = menuRepository.findById(id);
        checkCondition(optMenuPo.isPresent(), "记录不存在,id=" + id);

        MenuPo menuPo = optMenuPo.get();

        String name = menuPo.getName();
        checkNotBlank(name, "菜单名称不能为空");
        menuPo.setName(name);

        String description = menuPo.getDescription();
        menuPo.setDescription(description);

        String url = menuPo.getUrl();
        menuPo.setUrl(url);

        String icon = menuPo.getIcon();
        menuPo.setIcon(icon);

        Integer parentId = reqVo.getParentId();
        if (parentId != null) {
            Optional<MenuPo> optParentMenuPo = menuRepository.findById(parentId);
            checkCondition(optParentMenuPo.isPresent(), "父菜单不存在,parentId=" + parentId);

            MenuPo parentMenuPo = optParentMenuPo.get();
            menuPo.setParent(parentMenuPo);
        } else {
            menuPo.setParent(null);
        }

        menuPo.setUpdateTime(now());

        menuRepository.save(menuPo);
    }

    @Override
    public void deleteMenu(List<Integer> ids) {
        checkNotEmpty(ids, "记录id列表不能为空");

        List<MenuPo> menuPos = menuRepository.findAllById(ids);
        checkNotEmpty(menuPos, "记录不存在,ids=" + ids);

        menuPos.forEach(o -> {
            if (o.getDeleted()) {
                fail("记录已删除,id=" + o.getId());
            }

            o.setDeleted(true);
            o.setUpdateTime(now());
        });

        menuRepository.saveAll(menuPos);
    }
}
