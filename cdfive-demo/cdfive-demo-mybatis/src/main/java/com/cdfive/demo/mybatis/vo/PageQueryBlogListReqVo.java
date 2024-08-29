package com.cdfive.demo.mybatis.vo;

import com.cdfive.demo.mybatis.base.PageReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author cdfive
 */
@ApiModel("分页查询博客列表请求vo")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageQueryBlogListReqVo extends PageReqVo {

    private static final long serialVersionUID = 2415671998505749337L;

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("是否发布")
    private Boolean publish;
}
