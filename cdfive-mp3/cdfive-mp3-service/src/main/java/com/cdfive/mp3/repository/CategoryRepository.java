package com.cdfive.mp3.repository;

import com.cdfive.mp3.po.CategoryPo;
import com.cdfive.support.jpa.repository.BaseRepository;

/**
 * @author cdfive
 */
public interface CategoryRepository extends BaseRepository<CategoryPo, Integer>, CategoryRepositoryCustom {


}
