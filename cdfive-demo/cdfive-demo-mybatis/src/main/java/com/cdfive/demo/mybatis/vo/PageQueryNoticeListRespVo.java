package com.cdfive.demo.mybatis.vo;

import com.cdfive.demo.mybatis.enums.ClientType;
import com.cdfive.demo.mybatis.enums.NoticeScene;
import com.cdfive.demo.mybatis.enums.NoticeStatus;
import com.cdfive.demo.mybatis.enums.NoticeType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;

/**
 * @author cdfive
 */
@ApiModel("分页查询公告列表响应vo")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageQueryNoticeListRespVo implements Serializable {

    private static final long serialVersionUID = 4517853868662119045L;

    @ApiModelProperty("公告id")
    private Long id;

    @ApiModelProperty("公告标题")
    private String title;

    @ApiModelProperty("公告类型,TEXT-文本公告,IMAGE-图片弹窗")
    private NoticeType type;

    @ApiModelProperty("公告类型文案,文本公告,图片弹窗")
    private String typeText;

    @ApiModelProperty("客户端类型集合,WEXIN_APPLET-微信小程序")
    private LinkedHashSet<ClientType> clientTypes;

    @ApiModelProperty("场景集合,FIRST_OPEN_HOME_PAGE-首次打开首页")
    private LinkedHashSet<NoticeScene> scenes;

    @ApiModelProperty("生效开始时间")
    private String effectiveStartTime;

    @ApiModelProperty("生效结束时间")
    private String effectiveEndTime;

    @ApiModelProperty("公告封面")
    private String coverImage;

    @ApiModelProperty("弹窗次数")
    private Integer popupWindowTimes;

    @ApiModelProperty("公告状态,NEW-新建,ENABLE-启用,DISABLE-禁用")
    private NoticeStatus status;

    @ApiModelProperty("公告状态文案")
    private String statusText;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("创建人描述")
    private String createUserDesc;

    @ApiModelProperty("创建时间")
    @JsonFormat(locale = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("修改人")
    private String updateUser;

    @ApiModelProperty("修改人描述")
    private String updateUserDesc;

    @JsonFormat(locale = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;
}
