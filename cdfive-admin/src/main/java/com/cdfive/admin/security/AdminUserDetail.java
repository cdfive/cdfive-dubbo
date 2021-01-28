package com.cdfive.admin.security;

import com.cdfive.user.vo.menu.MenuVo;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author cdfive
 */
@Data
public class AdminUserDetail implements UserDetails {

    private String username;

    private String password;

    private List<MenuVo> menus;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (CollectionUtils.isEmpty(menus)) {
            return Collections.emptyList();
        }

//        menus.stream().flatMap(o -> )

        List<AdminGrantedAuthority> authorities = Lists.newArrayList();
        for (MenuVo menu : menus) {
            if (StringUtils.isNotBlank(menu.getUrl())) {
                authorities.add(new AdminGrantedAuthority(menu.getUrl()));
            }

            List<MenuVo> subMenus = menu.getSubMenus();
            if (!CollectionUtils.isEmpty(subMenus)) {
                for (MenuVo subMenu : subMenus) {
                    if (StringUtils.isNotBlank(subMenu.getUrl())) {
                        authorities.add(new AdminGrantedAuthority(subMenu.getUrl()));
                    }
                }
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
