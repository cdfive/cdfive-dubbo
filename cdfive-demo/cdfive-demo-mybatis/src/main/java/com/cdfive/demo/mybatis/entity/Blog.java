package com.cdfive.demo.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdfive.demo.mybatis.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author cdfive
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(value = "t_blog", autoResultMap = true)
public class Blog extends BaseEntity {

    private static final long serialVersionUID = 4301164052114847957L;

    /**
     * 分类编码
     */
    @TableField
    private String categoryCode;

    /**
     * 标题
     */
    @TableField
    private String title;

    /**
     * 内容
     */
    @TableField
    private String content;

    /**
     * 是否发布
     */
    @TableField
    private Boolean publish;

    /**
     * 发布时间
     */
    @TableField
    private LocalDateTime publishTime;
}
