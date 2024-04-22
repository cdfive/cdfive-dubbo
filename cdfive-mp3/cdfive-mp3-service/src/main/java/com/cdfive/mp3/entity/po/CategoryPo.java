package com.cdfive.mp3.entity.po;

import com.cdfive.support.jpa.po.BasePo;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "cdfive_category")
public class CategoryPo extends BasePo<Integer> {

    @OneToMany(mappedBy = "categoryPo", cascade = CascadeType.ALL)
    private List<CategorySongPo> categorySongPos;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CategoryPo categoryPo;

    @Column(name = "category_name")
    private String categoryName;

    private String description;

    private Integer sort;

    @Override
    public String toString() {
        return id != null ? String.valueOf(id) : null;
    }
}