package com.cdfive.mp3.vo.category;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cdfive
 */
@Data
public class AddCategoryReqVo implements Serializable {

    private static final long serialVersionUID = -146289504426875207L;

    private String name;

    private String description;

    private Integer sort;
}
