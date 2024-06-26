package com.cdfive.demo.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author cdfive
 */
@ApiModel("APP端关闭公告请求Vo")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppCloseNoticeReqVo implements Serializable {

    private static final long serialVersionUID = 1907278362897546481L;

    @ApiModelProperty("用户id")
    private Long memberId;

    @ApiModelProperty("客户端类型")
    private String clientType;

    @ApiModelProperty("场景")
    private String scene;

    @ApiModelProperty("公告id")
    private Long noticeId;
}
