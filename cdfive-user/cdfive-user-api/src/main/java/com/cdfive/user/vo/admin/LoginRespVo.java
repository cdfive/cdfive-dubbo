package com.cdfive.user.vo.admin;

import com.cdfive.user.vo.menu.MenuVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author cdfive
 */
@Data
public class LoginRespVo implements Serializable {

    private static final long serialVersionUID = -7590174514619169526L;

    private String token;

    private List<MenuVo> menus;
}
