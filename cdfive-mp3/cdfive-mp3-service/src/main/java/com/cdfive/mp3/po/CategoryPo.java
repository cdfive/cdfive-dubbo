package com.cdfive.mp3.po;

import com.cdfive.common.base.AbstractPo;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(name = "cdfive_category")
public class CategoryPo extends AbstractPo<Integer> {

    @OneToMany(mappedBy = "categoryPo")
    private List<CategorySongPo> categorySongPos;

    @Column(name = "category_name")
    private String categoryName;

    private String description;

    private Integer sort;
}