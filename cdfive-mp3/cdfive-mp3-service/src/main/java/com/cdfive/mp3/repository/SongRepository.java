package com.cdfive.mp3.repository;

import com.cdfive.mp3.po.SongPo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author cdfive
 */
public interface SongRepository extends JpaRepository<SongPo, Integer>, SongRepositoryCustom {


}
