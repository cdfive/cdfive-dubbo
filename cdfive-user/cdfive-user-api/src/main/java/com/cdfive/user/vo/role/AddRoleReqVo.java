package com.cdfive.user.vo.role;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cdfive
 */
@Data
public class AddRoleReqVo implements Serializable {

    private String name;

    private String description;

    private Boolean enable;
}
