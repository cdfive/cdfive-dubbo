package com.cdfive.user.vo.menu;

import lombok.Data;

/**
 * @author cdfive
 */
@Data
public class UpdateMenuReqVo {

    private Integer id;

    private Integer parentId;

    private String name;

    private String description;

    private String url;

    private String icon;
}
