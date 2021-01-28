package com.cdfive.user.vo.admin;

import com.cdfive.common.vo.page.PageReqVo;
import com.cdfive.user.enums.AdminStatusEnum;
import lombok.Data;

/**
 * @author cdfive
 */
@Data
public class QueryAdminListPageReqVo extends PageReqVo {

    private String name;

    private String mobile;

    private AdminStatusEnum status;
}
