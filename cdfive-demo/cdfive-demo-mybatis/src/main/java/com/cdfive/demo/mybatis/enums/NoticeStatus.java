package com.cdfive.demo.mybatis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 公告状态
 *
 * @author cdfive
 */
@AllArgsConstructor
@Getter
public enum NoticeStatus {

    NEW("新建"),

    ENABLE("启用"),

    DISABLE("禁用"),
    ;

    String desc;
}
