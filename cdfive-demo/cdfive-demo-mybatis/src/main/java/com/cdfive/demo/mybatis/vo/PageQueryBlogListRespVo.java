package com.cdfive.demo.mybatis.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@ApiModel("分页查询博客列表响应vo")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageQueryBlogListRespVo implements Serializable {

    private static final long serialVersionUID = 6369308889463980170L;

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("分类编码")
    private String categoryCode;

    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("是否发布")
    private Boolean publish;

    @ApiModelProperty("发布时间")
    @JsonFormat(locale = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;

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
