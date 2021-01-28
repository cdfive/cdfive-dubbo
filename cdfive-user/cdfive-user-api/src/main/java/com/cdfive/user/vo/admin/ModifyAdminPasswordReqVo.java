package com.cdfive.user.vo.admin;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cdfive
 */
@Data
public class ModifyAdminPasswordReqVo implements Serializable {

    private Integer id;

    private String oldPassword;

    private String newPassword;
}
