package com.cdfive.user.vo.role;

import com.cdfive.common.vo.page.PageReqVo;
import lombok.Data;

/**
 * @author cdfive
 */
@Data
public class QueryRoleListPageReqVo extends PageReqVo {

    private String name;

    private Boolean enable;
}
