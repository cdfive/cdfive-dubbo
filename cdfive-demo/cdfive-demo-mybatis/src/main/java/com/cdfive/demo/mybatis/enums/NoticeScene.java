package com.cdfive.demo.mybatis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 公告场景
 *
 * @author cdfive
 */
@AllArgsConstructor
@Getter
public enum NoticeScene {

    FIRST_OPEN_HOME_PAGE("首次打开首页"),
    ;

    String desc;
}
