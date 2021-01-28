package com.cdfive.user.vo.admin;

import com.cdfive.user.enums.AdminStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cdfive
 */
@Data
public class FindAdminDetailVo implements Serializable {

    private Integer id;

    private String username;

    private String password;

    private String aliasname;

    private String mobile;

    private AdminStatusEnum status;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
