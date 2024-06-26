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
@ApiModel("id")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QueryByIdReqVo implements Serializable {

    private static final long serialVersionUID = 8998044982088602898L;

    @ApiModelProperty(value = "id")
    private Long id;
}
