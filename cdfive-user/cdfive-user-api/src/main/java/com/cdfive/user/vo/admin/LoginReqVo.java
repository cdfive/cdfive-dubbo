package com.cdfive.user.vo.admin;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cdfive
 */
@Data
public class LoginReqVo implements Serializable {

    private static final long serialVersionUID = 1851928865413783678L;

    private String username;

    private String password;
}
