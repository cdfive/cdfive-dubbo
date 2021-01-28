package com.cdfive.user.vo.admin;

import com.cdfive.user.vo.menu.MenuVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author cdfive
 */
@Data
public class FindAdminByUsernameRespVo implements Serializable {

    private String username;

    private String password;

    private List<MenuVo> menus;
}
