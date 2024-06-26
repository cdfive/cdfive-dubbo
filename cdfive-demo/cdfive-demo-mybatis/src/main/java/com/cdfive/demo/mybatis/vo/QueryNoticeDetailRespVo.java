package com.cdfive.demo.mybatis.vo;

import com.cdfive.demo.mybatis.enums.ClientType;
import com.cdfive.demo.mybatis.enums.NoticeScene;
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
@ApiModel("查询公告详情响应vo")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QueryNoticeDetailRespVo implements Serializable {

    private static final long serialVersionUID = -9025995245137431978L;

    @ApiModelProperty("公告id")
    private Long id;

    @ApiModelProperty("公告标题")
    private String title;

    @ApiModelProperty("生效开始时间")
    @JsonFormat(locale = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime effectiveStartTime;

    @ApiModelProperty("生效结束时间")
    @JsonFormat(locale = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime effectiveEndTime;

    @ApiModelProperty("客户端类型集合,WEXIN_APPLET-微信小程序")
    private LinkedHashSet<ClientType> clientTypes;

    @ApiModelProperty("场景集合,FIRST_OPEN_HOME_PAGE-首次打开首页")
    private LinkedHashSet<NoticeScene> scenes;

    @ApiModelProperty("弹窗类型,TEXT-文本公告,IMAGE-图片公告")
    private NoticeType type;

    @ApiModelProperty("弹窗次数")
    private Integer popupWindowTimes;

    @ApiModelProperty("公告封面")
    private String coverImage;

    @ApiModelProperty("扩展数据,json格式[{\"imageUrl\":\"111\",\"jumpUrl\":\"111\"},{\"imageUrl\":\"222\",\"jumpUrl\":\"222\"}]")
    private String extData;

    @ApiModelProperty("公告内容")
    private String content;
}
