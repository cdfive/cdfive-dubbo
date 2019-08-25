package com.cdfive.mp3.repository;

import com.cdfive.mp3.po.SongPo;
import com.cdfive.support.jpa.repository.BaseRepository;

/**
 * @author cdfive
 */
public interface SongRepository extends BaseRepository<SongPo, Integer>, SongRepositoryCustom {


}
