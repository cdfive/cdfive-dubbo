package com.cdfive.demo.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.cdfive.demo.mybatis.base.BaseEntity;
import com.cdfive.demo.mybatis.enums.ClientType;
import com.cdfive.demo.mybatis.enums.NoticeScene;
import com.cdfive.demo.mybatis.enums.NoticeStatus;
import com.cdfive.demo.mybatis.enums.NoticeType;
import com.cdfive.demo.mybatis.typehandler.ClientTypeTypeHandler;
import com.cdfive.demo.mybatis.typehandler.NoticeSceneTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author cdfive
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(value = "t_notice", autoResultMap = true)
public class Notice extends BaseEntity {

    private static final long serialVersionUID = -2951574427906589299L;

    /**
     * 弹窗客户端集合
     */
    @TableField(typeHandler = ClientTypeTypeHandler.class)
    private Set<ClientType> clientTypes;

    /**
     * 弹窗场景集合
     */
    @TableField(typeHandler = NoticeSceneTypeHandler.class)
    private Set<NoticeScene> scenes;

    /**
     * 公告类型
     */
    @TableField
    private NoticeType type;

    /**
     * 标题
     */
    @TableField
    private String title;

    /**
     * 内容
     */
    @TableField
    private String content;

    /**
     * 状态
     */
    @TableField
    private NoticeStatus status;

    /**
     * 生效开始时间
     */
    @TableField
    private LocalDateTime effectiveStartTime;

    /**
     * 生效结束时间
     */
    @TableField
    private LocalDateTime effectiveEndTime;

    /**
     * 公告封面图片
     */
    @TableField
    private String coverImage;

    /**
     * 扩展数据,json格式[{"imageUrl":"111","jumpUrl":"111"},{"imageUrl":"222","jumpUrl":"222"}]
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private String extData;

    /**
     * 弹窗次数
     */
    @TableField
    private Integer popupWindowTimes;
}
