package com.cdfive.mp3.repository;

import com.cdfive.mp3.po.CategoryPo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author cdfive
 */
public interface CategoryRepository extends JpaRepository<CategoryPo, Integer>, CategoryRepositoryCustom {


}
