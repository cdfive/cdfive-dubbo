package com.cdfive.user.vo.menu;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author cdfive
 */
@Data
public class MenuVo implements Serializable {

    private Integer id;

    private String name;

    private String description;

    private String url;

    private String icon;

    private List<MenuVo> subMenus;
}
