package com.cdfive.mp3.repository;

import com.cdfive.mp3.po.CategorySongPo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author cdfive
 */
public interface CategorySongRepository extends JpaRepository<CategorySongPo, Integer>, CategorySongRepositoryCustom {


}
