package com.cdfive.user.repository;

import com.cdfive.support.jpa.repository.BaseRepository;
import com.cdfive.user.po.MenuPo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author cdfive
 */
public interface MenuRepository extends BaseRepository<MenuPo, Integer>, MenuRepositoryCustom, JpaSpecificationExecutor<MenuPo> {

}
