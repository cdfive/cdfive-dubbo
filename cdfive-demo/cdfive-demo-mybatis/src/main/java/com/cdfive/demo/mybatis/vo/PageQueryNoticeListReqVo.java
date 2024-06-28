package com.cdfive.demo.mybatis.vo;

import com.cdfive.demo.mybatis.base.PageReqVo;
import com.cdfive.demo.mybatis.enums.NoticeStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author cdfive
 */
@ApiModel("分页查询公告列表请求vo")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageQueryNoticeListReqVo extends PageReqVo {

    private static final long serialVersionUID = -3571247583035484417L;

    @ApiModelProperty("公告id")
    private String id;

    @ApiModelProperty("公告标题")
    private String title;

    @ApiModelProperty("公告状态,NEW-新建,ENABLE-启用,DISABLE-禁用")
//    private String status;
    private NoticeStatus status;

    @ApiModelProperty("公告类型")
    private String type;
}
