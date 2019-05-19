package com.cdfive.mp3.vo.category;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cdfive
 */
@Data
public class UpdateCategoryReqVo implements Serializable {

    private static final long serialVersionUID = 1318217203261090294L;

    private Integer id;

    private String name;

    private String description;

    private Integer sort;
}
