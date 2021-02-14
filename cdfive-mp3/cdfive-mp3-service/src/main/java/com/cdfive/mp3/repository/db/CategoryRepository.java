package com.cdfive.mp3.repository.db;

import com.cdfive.mp3.entity.po.CategoryPo;
import com.cdfive.support.jpa.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author cdfive
 */
public interface CategoryRepository extends BaseRepository<CategoryPo, Integer>, CategoryRepositoryCustom, JpaSpecificationExecutor {

    List<CategoryPo> findListByDeleted(Boolean deleted);
}
