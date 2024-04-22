package com.cdfive.user.service.impl;

import com.cdfive.common.util.JpaPageUtil;
import com.cdfive.common.util.MD5Util;
import com.cdfive.common.vo.page.BootstrapPageRespVo;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.framework.component.jwt.JwtComponent;
import com.cdfive.user.enums.AdminStatusEnum;
import com.cdfive.user.po.AdminPo;
import com.cdfive.user.po.MenuPo;
import com.cdfive.user.po.RolePo;
import com.cdfive.user.repository.AdminRepository;
import com.cdfive.user.repository.RoleRepository;
import com.cdfive.user.repository.specification.QueryAdminSpecification;
import com.cdfive.user.service.AbstractUserService;
import com.cdfive.user.service.AdminService;
import com.cdfive.user.transformer.QueryAdminListPageTransformer;
import com.cdfive.user.vo.admin.*;
import com.cdfive.user.vo.menu.MenuVo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
@Slf4j
@Service("adminService")
public class AdminServiceImpl extends AbstractUserService implements AdminService {

    @Autowired
    private JwtComponent jwtComponent;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public LoginRespVo login(LoginReqVo reqVo) {
        checkNotNull(reqVo, "请求参数不能为空");

        String username = reqVo.getUsername();
        checkNotBlank(username, "用户名不能为空");

        String password = reqVo.getPassword();
        checkNotBlank(password, "密码不能为空");

        AdminPo adminPo = adminRepository.findByUsername(username);
        checkNotNull(adminPo, "用户名或密码错误");

        checkCondition(Objects.equals(adminPo.getPassword(), MD5Util.encodeByMD5(password)), "用户名或密码错误");

        LoginRespVo respVo = new LoginRespVo();
        respVo.setName(adminPo.getAliasname());
        respVo.setMenus(Collections.emptyList());
        List<RolePo> roles = adminPo.getRoles();
        if (!CollectionUtils.isEmpty(roles)) {
            List<MenuVo> menuVos = Lists.newArrayList();
            respVo.setMenus(menuVos);

            List<MenuPo> menuPos = roles.stream().flatMap(o -> o.getMenus().stream()).collect(Collectors.toList());

            List<MenuPo> topMenuPos = menuPos.stream().filter(o -> o.getParent() == null).collect(Collectors.toList());
            List<MenuPo> subMenuPos = menuPos.stream().filter(o -> o.getParent() != null).collect(Collectors.toList());

            for (MenuPo topMenuPo : topMenuPos) {
                MenuVo menuVo = new MenuVo();
                menuVo.setId(topMenuPo.getId());
                menuVo.setName(topMenuPo.getName());
                menuVo.setDescription(topMenuPo.getDescription());
                menuVo.setUrl(topMenuPo.getUrl());
                menuVo.setIcon(topMenuPo.getIcon());
                menuVos.add(menuVo);

                List<MenuVo> subMenuVos = Lists.newArrayList();
                menuVo.setSubMenus(subMenuVos);

                for (MenuPo subMenuPo : subMenuPos) {
                    if (subMenuPo.getParent() != null && subMenuPo.getParent().getId().equals(menuVo.getId())) {
                        MenuVo subMenuVo = new MenuVo();
                        subMenuVo.setId(subMenuPo.getId());
                        subMenuVo.setName(subMenuPo.getName());
                        subMenuVo.setDescription(subMenuPo.getDescription());
                        subMenuVo.setUrl(subMenuPo.getUrl());
                        subMenuVo.setIcon(subMenuPo.getIcon());
                        subMenuVos.add(subMenuVo);
                    }
                }
            }
        }

        JwtComponent.JwtClaims jwtClaims = new JwtComponent.JwtClaims();
        jwtClaims.setUserId(Long.valueOf(adminPo.getId()));
        jwtClaims.setUserName(adminPo.getUsername());
        String token = jwtComponent.createToken(jwtClaims);
        respVo.setToken(token);
        return respVo;
    }

    @Override
    public FindAdminByUsernameRespVo findAdminByUsername(String username) {
        checkNotBlank(username, "用户名不能为空");

        AdminPo adminPo = adminRepository.findByUsername(username);
        checkNotNull(adminPo, "用户名或密码错误");

        FindAdminByUsernameRespVo adminVo = new FindAdminByUsernameRespVo();
        adminVo.setUsername(adminPo.getUsername());
        adminVo.setPassword(adminPo.getPassword());

        List<RolePo> roles = adminPo.getRoles();
        if (!CollectionUtils.isEmpty(roles)) {
            List<MenuVo> menuVos = Lists.newArrayList();
            adminVo.setMenus(menuVos);

            List<MenuPo> menuPos = roles.stream().flatMap(o -> o.getMenus().stream()).collect(Collectors.toList());

            List<MenuPo> topMenuPos = menuPos.stream().filter(o -> o.getParent() == null).collect(Collectors.toList());
            List<MenuPo> subMenuPos = menuPos.stream().filter(o -> o.getParent() != null).collect(Collectors.toList());

            for (MenuPo topMenuPo : topMenuPos) {
                MenuVo menuVo = new MenuVo();
                menuVo.setId(topMenuPo.getId());
                menuVo.setName(topMenuPo.getName());
                menuVo.setDescription(topMenuPo.getDescription());
                menuVo.setUrl(topMenuPo.getUrl());
                menuVo.setIcon(topMenuPo.getIcon());
                menuVos.add(menuVo);

                List<MenuVo> subMenuVos = Lists.newArrayList();
                menuVo.setSubMenus(subMenuVos);

                for (MenuPo subMenuPo : subMenuPos) {
                    if (subMenuPo.getParent() != null && subMenuPo.getParent().getId().equals(menuVo.getId())) {
                        MenuVo subMenuVo = new MenuVo();
                        subMenuVo.setId(subMenuPo.getId());
                        subMenuVo.setName(subMenuPo.getName());
                        subMenuVo.setDescription(subMenuPo.getDescription());
                        subMenuVo.setUrl(subMenuPo.getUrl());
                        subMenuVo.setIcon(subMenuPo.getIcon());
                        subMenuVos.add(subMenuVo);
                    }
                }
            }
        }
        return adminVo;
    }

    public FindAdminByUsernameRespVo findAdminByUsernameMock() {
        FindAdminByUsernameRespVo adminVo = new FindAdminByUsernameRespVo();
        adminVo.setUsername("1");
        adminVo.setPassword("c4ca4238a0b923820dcc509a6f75849b");
//        adminVo.setAuthorities(Lists.newArrayList("/mp3/list"));

        List<MenuVo> menuVos = Lists.newArrayList();
        MenuVo menuVo = new MenuVo();
        menuVo.setName("mp3管理");
        menuVo.setUrl("/mp3");

        List<MenuVo> subMenus = Lists.newArrayList();
        MenuVo subMenuVo = new MenuVo();
        subMenuVo.setName("分类列表");
        subMenuVo.setUrl("javascript:menuopen('/category/list')");
        subMenus.add(subMenuVo);

        subMenuVo = new MenuVo();
        subMenuVo.setName("mp3列表");
        subMenuVo.setUrl("javascript:menuopen('/mp3/list')");
        subMenus.add(subMenuVo);
        menuVo.setSubMenus(subMenus);

        menuVos.add(menuVo);

        adminVo.setMenus(menuVos);
        return adminVo;
    }

    @Override
    public PageRespVo<QueryAdminListPageRespVo> queryAdminListPage(QueryAdminListPageReqVo reqVo) {
        return JpaPageUtil.buildPage(reqVo, adminRepository, new QueryAdminSpecification(reqVo), QueryAdminListPageTransformer.INSTANCE);
    }

    @Override
    public BootstrapPageRespVo<QueryAdminListPageRespVo> queryAdminListBootstrapPage(QueryAdminListPageReqVo reqVo) {
        return JpaPageUtil.buildBootstrapPage(reqVo, adminRepository, new QueryAdminSpecification(reqVo), QueryAdminListPageTransformer.INSTANCE);
    }

    @Override
    public FindAdminDetailVo findAdminDetail(Integer id) {
        checkNotNull(id, "记录id不能为空");

        Optional<AdminPo> optAdminPo = adminRepository.findById(id);
        checkCondition(optAdminPo.isPresent(), "记录不存在,id=" + id);

        AdminPo adminPo = optAdminPo.get();
        FindAdminDetailVo detailVo = new FindAdminDetailVo();
        detailVo.setId(adminPo.getId());
        detailVo.setUsername(adminPo.getUsername());
        detailVo.setPassword(adminPo.getPassword());
        detailVo.setAliasname(adminPo.getAliasname());
        detailVo.setMobile(adminPo.getMobile());
        detailVo.setStatus(adminPo.getStatus());
        detailVo.setCreateTime(adminPo.getCreateTime());
        detailVo.setUpdateTime(adminPo.getUpdateTime());
        return detailVo;
    }

    @Override
    public Integer addAdmin(AddAdminReqVo reqVo) {
        checkNotNull(reqVo, "请求参数不能为空");

        AdminPo adminPo = new AdminPo();

        String username = reqVo.getUsername();
        checkNotBlank(username, "用户名不能为空");
        adminPo.setUsername(username);

        String password = reqVo.getPassword();
        checkNotBlank(password, "密码不能为空");
        adminPo.setPassword(MD5Util.encodeByMD5(password));

        String aliasname = reqVo.getAliasname();
        adminPo.setAliasname(aliasname);

        String mobile = reqVo.getMobile();
        adminPo.setMobile(mobile);

        adminPo.setStatus(AdminStatusEnum.NORMAL);

        adminPo.setDeleted(false);
        adminPo.setCreateTime(now());

        List<Integer> roleIds = reqVo.getRoleIds();
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<RolePo> rolePos = roleRepository.findAllById(roleIds);
            checkNotEmpty(rolePos, "角色不存在,ids=" + roleIds);

            adminPo.setRoles(rolePos);
        }

        adminRepository.save(adminPo);
        return adminPo.getId();
    }

    @Override
    public void updateAdmin(UpdateAdminReqVo reqVo) {
        checkNotNull(reqVo, "请求参数不能为空");

        Integer id = reqVo.getId();
        checkNotNull(id, "记录id不能为空");

        Optional<AdminPo> optAdminPo = adminRepository.findById(id);
        checkCondition(optAdminPo.isPresent(), "记录不存在,id=" + id);

        AdminPo adminPo = optAdminPo.get();

        String aliasname = reqVo.getAliasname();
        adminPo.setAliasname(aliasname);

        String mobile = reqVo.getMobile();
        adminPo.setMobile(mobile);

        adminPo.setUpdateTime(now());

        List<Integer> roleIds = reqVo.getRoleIds();
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<RolePo> rolePos = roleRepository.findAllById(roleIds);
            checkNotEmpty(rolePos, "角色不存在,ids=" + roleIds);

            adminPo.setRoles(rolePos);
        } else {
            adminPo.setRoles(null);
        }

        adminRepository.save(adminPo);
    }

    @Override
    public void deleteAdmin(List<Integer> ids) {
        checkNotEmpty(ids, "记录id列表不能为空");

        List<AdminPo> adminPos = adminRepository.findAllById(ids);
        checkNotEmpty(adminPos, "记录不存在,ids=" + ids);

        adminPos.forEach(o -> {
            if (o.getDeleted()) {
                fail("记录已删除,id=" + o.getId());
            }

            o.setDeleted(true);
            o.setUpdateTime(now());
        });

        adminRepository.saveAll(adminPos);
    }

    @Override
    public void modifyAdminPassword(ModifyAdminPasswordReqVo reqVo) {
        checkNotNull(reqVo, "请求参数不能为空");

        Integer id = reqVo.getId();
        checkNotNull(id, "记录id不能为为空");

        String oldPassword = reqVo.getOldPassword();
        checkNotBlank(oldPassword, "原密码不能为空");

        String newPassword = reqVo.getNewPassword();
        checkNotBlank(newPassword, "新密码不能为空");

        Optional<AdminPo> optAdminPo = adminRepository.findById(id);
        checkCondition(optAdminPo.isPresent(), "记录不存在,id=" + id);

        AdminPo adminPo = optAdminPo.get();
        if (!adminPo.getPassword().equals(MD5Util.encodeByMD5(oldPassword))) {
            fail("原密码错误");
        }

        String encodeNewPassword = MD5Util.encodeByMD5(newPassword);
        if (adminPo.getPassword().equals(encodeNewPassword)) {
            fail("原密码和新密码不能相同");
        }

        adminPo.setPassword(encodeNewPassword);
        adminPo.setUpdateTime(now());
        adminRepository.save(adminPo);
    }
}
