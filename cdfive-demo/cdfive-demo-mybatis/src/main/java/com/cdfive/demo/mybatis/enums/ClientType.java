package com.cdfive.demo.mybatis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 客户端类型
 *
 * @author cdfve
 */
@AllArgsConstructor
@Getter
public enum ClientType {

    WEXIN_APPLET("微信小程序"),
    ;

    String desc;
}
