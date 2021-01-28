package com.cdfive.user.service.impl;

import com.cdfive.common.util.JpaPageUtil;
import com.cdfive.common.vo.page.BootstrapPageRespVo;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.user.po.MenuPo;
import com.cdfive.user.po.RolePo;
import com.cdfive.user.repository.MenuRepository;
import com.cdfive.user.repository.RoleRepository;
import com.cdfive.user.repository.specification.QueryRoleSpecification;
import com.cdfive.user.service.AbstractUserService;
import com.cdfive.user.service.RoleService;
import com.cdfive.user.transformer.QueryRoleListPageTransformer;
import com.cdfive.user.vo.role.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author cdfive
 */
@Slf4j
@Service("roleService")
public class RoleServiceImpl extends AbstractUserService implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MenuRepository menuRepository;

    @Override
    public PageRespVo<QueryRoleListPageRespVo> queryRoleListPage(QueryRoleListPageReqVo reqVo) {
        return JpaPageUtil.buildPage(reqVo, roleRepository, new QueryRoleSpecification(reqVo), QueryRoleListPageTransformer.INSTANCE);
    }

    @Override
    public BootstrapPageRespVo<QueryRoleListPageRespVo> queryRoleListBootstrapPage(QueryRoleListPageReqVo reqVo) {
        return JpaPageUtil.buildBootstrapPage(reqVo, roleRepository, new QueryRoleSpecification(reqVo), QueryRoleListPageTransformer.INSTANCE);
    }

    @Override
    public FindRoleDetailVo findRoleDetail(Integer id) {
        checkNotNull(id, "记录id不能为空");

        Optional<RolePo> optRolePo = roleRepository.findById(id);
        checkCondition(optRolePo.isPresent(), "记录不存在,id=" + id);

        RolePo rolePo = optRolePo.get();
        FindRoleDetailVo detailVo = new FindRoleDetailVo();
        detailVo.setId(rolePo.getId());
        detailVo.setName(rolePo.getName());
        detailVo.setDescription(rolePo.getDescription());
        detailVo.setEnable(rolePo.getEnable());
        detailVo.setCreateTime(rolePo.getCreateTime());
        detailVo.setUpdateTime(rolePo.getUpdateTime());
        return detailVo;
    }

    @Override
    public Integer addRole(AddRoleReqVo reqVo) {
        checkNotNull(reqVo, "请求参数不能为空");

        RolePo rolePo = new RolePo();

        String name = reqVo.getName();
        checkNotBlank(name, "分类名称不能为空");
        rolePo.setName(name);

        String description = reqVo.getDescription();
        rolePo.setDescription(description);

        Boolean enable = reqVo.getEnable();
        rolePo.setEnable(enable);

        rolePo.setDeleted(false);
        rolePo.setCreateTime(now());

        roleRepository.save(rolePo);
        return rolePo.getId();
    }

    @Override
    public void updateRole(UpdateRoleReqVo reqVo) {
        checkNotNull(reqVo, "请求参数不能为空");

        Integer id = reqVo.getId();
        checkNotNull(id, "记录id不能为空");

        Optional<RolePo> optRolePo = roleRepository.findById(id);
        checkNotNull(optRolePo, "记录不存在,id=" + id);

        RolePo rolePo = optRolePo.get();

        String name = reqVo.getName();
        checkNotBlank(name, "分类名称不能为空");
        rolePo.setName(name);

        String description = reqVo.getDescription();
        rolePo.setDescription(description);

        Boolean enable = reqVo.getEnable();
        rolePo.setEnable(enable);

        rolePo.setUpdateTime(now());

        roleRepository.save(rolePo);
    }

    @Override
    public void deleteRole(List<Integer> ids) {
        checkNotEmpty(ids, "记录id列表不能为空");

        List<RolePo> rolePos = roleRepository.findAllById(ids);
        checkNotEmpty(rolePos, "记录不存在,ids=" + ids);

        rolePos.forEach(o -> {
            if (o.getDeleted()) {
                fail("记录已删除,id=" + o.getId());
            }
            o.setDeleted(true);
            o.setUpdateTime(now());
        });

        roleRepository.saveAll(rolePos);
    }

    @Override
    public void addRoleMenu(AddRoleMenuReqVo reqVo) {
        checkNotNull(reqVo, "请求参数不能为空");

        Integer roleId = reqVo.getRoleId();
        checkNotNull(reqVo, "角色id不能为空");

        Optional<RolePo> optRolePo = roleRepository.findById(roleId);
        checkCondition(optRolePo.isPresent(), "角色不存在,id=" + roleId);
        RolePo rolePo = optRolePo.get();

        List<Integer> menuIds = reqVo.getMenuIds();
        if (CollectionUtils.isEmpty(menuIds)) {
            rolePo.setMenus(null);
        } else {
            List<MenuPo> menuPos = menuRepository.findAllById(menuIds);
            checkNotEmpty(menuPos, "菜单不存在,ids=" + menuIds);

            rolePo.setMenus(menuPos);
        }
        roleRepository.save(rolePo);
    }
}
