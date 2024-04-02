package com.cdfive.mp3.entity.po;

import com.cdfive.support.jpa.po.BasePo;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cdfive_category_song", uniqueConstraints = {@UniqueConstraint(columnNames = {"category_id", "song_id"})})
public class CategorySongPo extends BasePo<Integer> {

    @ManyToOne
//    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryPo categoryPo;

    @ManyToOne
//    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    private SongPo songPo;
}
