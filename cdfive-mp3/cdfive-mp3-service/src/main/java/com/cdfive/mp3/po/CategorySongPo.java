package com.cdfive.mp3.po;

import com.cdfive.common.base.AbstractPo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "cdfive_category_song")
public class CategorySongPo extends AbstractPo<Integer> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryPo categoryPo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    private SongPo songPo;
}
