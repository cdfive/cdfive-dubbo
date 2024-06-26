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
@ApiModel("APP端查询最新公告请求Vo")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppQueryLatestNoticeReqVo implements Serializable {

    private static final long serialVersionUID = 5300967121184979732L;

    @ApiModelProperty("用户id")
    private Long memberId;

    @ApiModelProperty("客户端类型")
    private String clientType;

    @ApiModelProperty("场景")
    private String scene;
}
