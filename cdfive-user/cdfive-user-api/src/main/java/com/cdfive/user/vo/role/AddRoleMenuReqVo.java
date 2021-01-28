package com.cdfive.user.vo.role;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author cdfive
 */
@Data
public class AddRoleMenuReqVo implements Serializable {

    private Integer roleId;

    private List<Integer> menuIds;
}
