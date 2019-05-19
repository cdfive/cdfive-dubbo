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
@Table(name = "cdfive_song")
public class SongPo extends AbstractPo<Integer> {

    @OneToMany(mappedBy = "songPo")
    private List<CategorySongPo> songCategoryPos;

    @Column(name = "song_name")
    private String songName;

    @Column(name = "full_name")
    private String fullName;

    private String author;

    private String description;

    private String path;

    private Integer digit;

    private String reason;

    @Column(name = "play_count")
    private Integer playCount;

    private Integer sort;
}
