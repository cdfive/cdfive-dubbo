package com.cdfive.admin.security;

import com.cdfive.user.service.AdminService;
import com.cdfive.user.vo.admin.FindAdminByUsernameRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author cdfive
 */
@Service
public class AdminUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FindAdminByUsernameRespVo adminVo = adminService.findAdminByUsername(username);

        if (adminVo == null) {
            throw new UsernameNotFoundException(username + " not found");
        }

//        return new User(username, adminVo.getPassword(), AuthorityUtils.createAuthorityList(adminVo.getAuthorities().toArray(new String[0])));

        AdminUserDetail adminUserDetail = new AdminUserDetail();
        adminUserDetail.setUsername(adminVo.getUsername());
        adminUserDetail.setPassword(adminVo.getPassword());
        adminUserDetail.setMenus(adminVo.getMenus());
        return adminUserDetail;
    }
}
