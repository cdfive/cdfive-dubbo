package com.cdfive.mp3.repository;

import com.cdfive.mp3.po.CategorySongPo;
import com.cdfive.support.jpa.repository.BaseRepository;

/**
 * @author cdfive
 */
public interface CategorySongRepository extends BaseRepository<CategorySongPo, Integer>, CategorySongRepositoryCustom {


}
