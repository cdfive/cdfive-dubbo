package com.cdfive.demo.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdfive.demo.mybatis.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cdfive
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(value = "t_category", autoResultMap = true)
public class Category extends BaseEntity {

    private static final long serialVersionUID = -4269997029799187111L;

    /**
     * 分类编码
     */
    @TableField
    private String categoryCode;

    /**
     * 分类名称
     */
    @TableField
    private String categoryName;
}
