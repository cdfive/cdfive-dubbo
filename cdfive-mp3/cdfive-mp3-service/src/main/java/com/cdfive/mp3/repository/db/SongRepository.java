package com.cdfive.mp3.repository.db;

import com.cdfive.mp3.entity.po.SongPo;
import com.cdfive.support.jpa.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author cdfive
 */
public interface SongRepository extends BaseRepository<SongPo, Integer>, SongRepositoryCustom, JpaSpecificationExecutor<SongPo> {


}
