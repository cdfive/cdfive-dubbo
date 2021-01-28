package com.cdfive.user.vo.menu;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cdfive
 */
@Data
public class AddMenuReqVo implements Serializable {

    private Integer parentId;

    private String name;

    private String description;

    private String url;

    private String icon;
}
