package com.cdfive.demo.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedHashSet;

/**
 * @author cdfive
 */
@ApiModel("保存公告请求vo")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaveNoticeReqVo implements Serializable {

    private static final long serialVersionUID = -305175373420683789L;

    @ApiModelProperty("公告id")
    private Long id;

    @ApiModelProperty("公告标题")
    private String title;

    @ApiModelProperty("生效开始时间")
    private String effectiveStartTime;

    @ApiModelProperty("生效结束时间")
    private String effectiveEndTime;

    @ApiModelProperty("客户端类型集合,WEXIN_APPLET-微信小程序")
    private LinkedHashSet<String> clientTypes;

    @ApiModelProperty("场景集合,FIRST_OPEN_HOME_PAGE-首次打开首页")
    private LinkedHashSet<String> scenes;

    @ApiModelProperty("公告类型,TEXT-文本公告,IMAGE-图片公告")
    private String type;

    @ApiModelProperty("弹窗次数")
    private Integer popupWindowTimes;

    @ApiModelProperty("公告封面")
    private String coverImage;

    @ApiModelProperty("扩展数据,json格式[{\"imageUrl\":\"111\",\"jumpUrl\":\"111\"},{\"imageUrl\":\"222\",\"jumpUrl\":\"222\"}]")
    private String extData;

    @ApiModelProperty("公告内容")
    private String content;
}
