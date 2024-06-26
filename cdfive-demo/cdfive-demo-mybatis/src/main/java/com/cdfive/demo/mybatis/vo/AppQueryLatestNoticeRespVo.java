package com.cdfive.demo.mybatis.vo;

import com.cdfive.demo.mybatis.enums.NoticeType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author cdfive
 */
@ApiModel("APP端查询最新公告响应Vo")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppQueryLatestNoticeRespVo implements Serializable {

    private static final long serialVersionUID = 2568067974470788881L;

    @ApiModelProperty("公告id")
    private Long id;

    @ApiModelProperty("公告类型,TEXT-文本公告,IMAGE-图片公告")
    private NoticeType type;

    @ApiModelProperty("公告类型文案,文本公告,图片公告")
    private String typeText;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("公告内容")
    private String content;

    @ApiModelProperty("公告封面")
    private String coverImage;

    @ApiModelProperty("扩展数据,json格式[{\"imageUrl\":\"111\",\"jumpUrl\":\"111\"},{\"imageUrl\":\"222\",\"jumpUrl\":\"222\"}]")
    private String extData;

    @JsonIgnore
    @ApiModelProperty(value = "生效开始时间", hidden = true)
    private LocalDateTime effectiveStartTime;

    @JsonIgnore
    @ApiModelProperty(value = "生效结束时间", hidden = true)
    private LocalDateTime effectiveEndTime;

    @JsonIgnore
    @ApiModelProperty(value = "弹窗次数", hidden = true)
    private Integer popupWindowTimes;
}
