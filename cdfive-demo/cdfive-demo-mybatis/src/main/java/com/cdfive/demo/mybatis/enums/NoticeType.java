package com.cdfive.demo.mybatis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 公告类型
 *
 * @author cdfive
 */
@AllArgsConstructor
@Getter
public enum NoticeType {

    TEXT("文本"),

    IMAGE("图片"),
    ;

    String desc;
}
