package com.cdfive.mp3.repository.impl;

import com.cdfive.mp3.repository.SongRepositoryCustom;
import com.cdfive.mp3.vo.song.SongListVo;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author cdfive
 */
@Repository
public class SongRepositoryImpl implements SongRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<SongListVo> findSongListByCategoryName(String categoryName) {
        StringBuilder sql = new StringBuilder();
        sql.append("select s.id,s.song_name as songName,s.full_name as fullName,s.author,s.description,s.path,s.digit,s.reason")
                .append(" from cdfive_song s")
                .append(" join cdfive_category_song cs on s.id=cs.song_id")
                .append(" join cdfive_category c on cs.category_id=c.id")
                .append(" where c.category_name=:categoryName")
                .append(" order by s.sort");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("categoryName", categoryName);

        List<SongListVo> list = query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(SongListVo.class)).list();
        return list;
    }

    @Override
    public List<SongListVo> findSongListByCategoryId(Integer categoryId) {
        StringBuilder sql = new StringBuilder();
        sql.append("select s.id,s.song_name as songName,s.full_name as fullName,s.author,s.description,s.path,s.digit,s.reason")
           .append(" from cdfive_song s")
           .append(" join cdfive_category_song cs on s.id=cs.song_id")
           .append(" where cs.category_id=:categoryId")
           .append(" order by s.sort");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("categoryId", categoryId);

        List<SongListVo> list = query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(SongListVo.class)).list();
        return list;
    }

    @Override
    public List<SongListVo> findSongListByDigit(Integer digit) {
        StringBuilder sql = new StringBuilder();
        sql.append("select s.id,s.song_name as songName,s.full_name as fullName,s.author,s.description,s.path,s.digit,s.reason")
                .append(" from cdfive_song s")
                .append(" where s.digit=:digit")
                .append(" order by s.sort");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("digit", digit);

        List<SongListVo> list = query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(SongListVo.class)).list();
        return list;
    }

    @Override
    public List<SongListVo> findRandomSongList(Integer num) {
        StringBuilder sql = new StringBuilder();
        sql.append("select s.id,s.song_name as songName,s.full_name as fullName,s.author,s.description,s.path,s.digit,s.reason")
                .append(" from cdfive_song s")
                .append(" order by rand()")
                .append(" limit :num");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("num", num);

        List<SongListVo> list = query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(SongListVo.class)).list();
        return list;
    }
}
