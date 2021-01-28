package com.cdfive.user.repository;

import com.cdfive.support.jpa.repository.BaseRepository;
import com.cdfive.user.po.RolePo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author cdfive
 */
public interface RoleRepository extends BaseRepository<RolePo, Integer>, RoleRepositoryCustom, JpaSpecificationExecutor<RolePo> {

}
