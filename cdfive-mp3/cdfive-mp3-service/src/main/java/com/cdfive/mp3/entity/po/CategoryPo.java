package com.cdfive.mp3.entity.po;

import com.cdfive.support.jpa.po.BasePo;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(name = "cdfive_category")
public class CategoryPo extends BasePo<Integer> {

    @OneToMany(mappedBy = "categoryPo")
    private List<CategorySongPo> categorySongPos;

    @Column(name = "category_name")
    private String categoryName;

    private String description;

    private Integer sort;
}