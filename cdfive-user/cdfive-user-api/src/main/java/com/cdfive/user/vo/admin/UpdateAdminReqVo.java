package com.cdfive.user.vo.admin;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author cdfive
 */
@Data
public class UpdateAdminReqVo implements Serializable {

    private Integer id;

    private String aliasname;

    private String mobile;

    private List<Integer> roleIds;
}
