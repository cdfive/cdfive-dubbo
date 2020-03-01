package com.cdfive.common.vo.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author cdfive
 */
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(staticName = "of")
@Data
public class PageReqVo implements Serializable {

    private Integer pageNum;

    private Integer pageSize;
}
