package com.cdfive.user.repository;

import com.cdfive.support.jpa.repository.BaseRepository;
import com.cdfive.user.po.AdminPo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author cdfive
 */
public interface AdminRepository extends BaseRepository<AdminPo, Integer>, AdminRepositoryCustom, JpaSpecificationExecutor<AdminPo> {

    AdminPo findByUsername(String username);
}
