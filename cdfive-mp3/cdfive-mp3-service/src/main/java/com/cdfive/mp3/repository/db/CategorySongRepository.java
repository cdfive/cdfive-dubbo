package com.cdfive.mp3.repository.db;

import com.cdfive.mp3.entity.po.CategorySongPo;
import com.cdfive.support.jpa.repository.BaseRepository;

/**
 * @author cdfive
 */
public interface CategorySongRepository extends BaseRepository<CategorySongPo, Integer>, CategorySongRepositoryCustom {


}
