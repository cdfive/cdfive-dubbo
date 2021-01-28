package com.cdfive.user.vo.admin;

import com.cdfive.user.enums.AdminStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author cdfive
 */
@Data
public class AddAdminReqVo implements Serializable {

    private String username;

    private String password;

    private String aliasname;

    private String mobile;

    private List<Integer> roleIds;
}
