package com.cdfive.mp3.service.impl;

import com.cdfive.common.util.PageUtil;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.log.service.BizLogService;
import com.cdfive.mp3.entity.po.CategoryPo;
import com.cdfive.mp3.entity.po.CategorySongPo;
import com.cdfive.mp3.entity.po.SongPo;
import com.cdfive.mp3.repository.db.CategoryRepository;
import com.cdfive.mp3.repository.db.SongRepository;
import com.cdfive.mp3.repository.db.specification.QuerySongSpecification;
import com.cdfive.mp3.service.AbstractMp3Service;
import com.cdfive.mp3.service.SongService;
import com.cdfive.mp3.transformer.QuerySongListPageTransformer;
import com.cdfive.mp3.vo.song.*;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
@Slf4j
@Service("songService")
public class SongServiceImpl extends AbstractMp3Service implements SongService {

    private LoadingCache<String, List<SongListVo>> songListByCategoryNameCache = CacheBuilder.newBuilder()
            .refreshAfterWrite(1, TimeUnit.MINUTES)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .maximumSize(10)
            .build(new CacheLoader<String, List<SongListVo>>() {
                @Override
                public List<SongListVo> load(String categoryName) throws Exception {
                    return songRepository.findSongListByCategoryName(categoryName);
                }
            });

    @Autowired
    private BizLogService bizLogService;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Cacheable(value = "song", key = "'all'")
    @Override
    public FindAllSongRespVo findAllSong() {
        log.info("songService=>findAllSong");
        FindAllSongRespVo respVo = new FindAllSongRespVo();

        respVo.setD2019(songListByCategoryNameCache.getUnchecked("2019"));
        respVo.setD2018(songListByCategoryNameCache.getUnchecked("2018"));
        respVo.setD2017(songListByCategoryNameCache.getUnchecked("2017"));
        respVo.setD2016(songListByCategoryNameCache.getUnchecked("2016"));

        respVo.setD1(songRepository.findSongListByDigit(1));
        respVo.setD2(songRepository.findSongListByDigit(2));
        respVo.setD3(songRepository.findSongListByDigit(3));
        respVo.setD4(songRepository.findSongListByDigit(4));
        respVo.setD5(songRepository.findSongListByDigit(5));

        respVo.setDYu(songRepository.findSongListByCategoryName("鱼"));
        respVo.setDLiZhi(songRepository.findSongListByCategoryName("励志"));
        respVo.setDAiQing(songRepository.findSongListByCategoryName("爱情"));
        respVo.setDWenNuan(songRepository.findSongListByCategoryName("温暖"));
        respVo.setDMan(songRepository.findSongListByCategoryName("Man"));

        respVo.setDEn(songRepository.findSongListByCategoryName("英语"));
        respVo.setDYy(songRepository.findSongListByCategoryName("粤语"));
        respVo.setDWt(songRepository.findSongListByCategoryName("无题"));
        respVo.setDDqxh(songRepository.findSongListByCategoryName("单曲循环"));
        respVo.setDSpec(songRepository.findSongListByCategoryName("特别"));

        return respVo;
    }

    @Cacheable(value = "song", key = "'random'")
    @Override
    public List<SongListVo> findRandomSongList(Integer num) {
        log.info("songService=>findRandomSongList");
        checkCondition(num > 0, "数量应大于0");

        List<SongListVo> list = songRepository.findRandomSongList(num);
        return list;
    }

    @Override
    public Integer play(Integer id, String ip) {
        log.info("songService=>play");
        checkNotNull(id, "id不能为空");

        bizLogService.addBizLog("播放mp3", id, ip);

        SongPo songPo = songRepository.getOne(id);
        checkCondition(songPo != null, "记录不存在");
        checkCondition(!songPo.getDeleted(), "记录已删除");

        Integer playCount = songPo.getPlayCount();
        if (playCount == null) {
            playCount = 0;
        }
        playCount++;
        songPo.setPlayCount(playCount);
        songRepository.save(songPo);
        return playCount;
    }

    @Override
    public PageRespVo<QuerySongListPageRespVo> querySongListPage(QuerySongListPageReqVo reqVo) {
        return PageUtil.buildPage(reqVo, songRepository, new QuerySongSpecification(reqVo), QuerySongListPageTransformer.INSTANCE);
    }

    @Override
    public Integer addSong(AddSongReqVo reqVo) {
        checkNotNull(reqVo, "请求参数不能为空");

        SongPo songPo = new SongPo();

        String name = reqVo.getName();
        checkNotEmpty(name, "歌名不能为空");
        songPo.setSongName(name);

        String author = reqVo.getAuthor();
        checkNotEmpty(author, "歌手不能为空");
        songPo.setAuthor(author);

        songPo.setFullName(name + "-" + author);

        String description = reqVo.getDescription();
        songPo.setDescription(description);

        String path = reqVo.getPath();
        checkNotEmpty(path, "文件路径不能为空");

        String reason = reqVo.getReason();
        songPo.setReason(reason);

        Integer sort = reqVo.getSort();
        songPo.setSort(sort);

        List<Integer> categoryIds = reqVo.getCategoryIds();
        if (isNotEmpty(categoryIds)) {
            List<CategoryPo> categoryPos = categoryRepository.findAllById(categoryIds);
            List<CategorySongPo> categorySongPos = categoryPos.stream().map(o -> {
                CategorySongPo categorySongPo = new CategorySongPo();
                categorySongPo.setSongPo(songPo);
                categorySongPo.setCategoryPo(o);
                return categorySongPo;
            }).collect(Collectors.toList());
            songPo.setSongCategoryPos(categorySongPos);
        }

        songPo.setCreateTime(now());
        songPo.setDeleted(false);

        songRepository.save(songPo);
        return songPo.getId();
    }

    @Override
    public void updateSong(UpdateSongReqVo reqVo) {
        checkNotNull(reqVo, "请求参数不能为空");

        Integer id = reqVo.getId();
        checkNotNull(id, "记录id不能为空");

        Optional<SongPo> optSongPo = songRepository.findById(id);
        checkCondition(optSongPo.isPresent(), "记录不存在,id=" + id);

        SongPo songPo = optSongPo.get();

        String name = reqVo.getName();
        checkNotEmpty(name, "歌名不能为空");
        songPo.setSongName(name);

        String author = reqVo.getAuthor();
        checkNotEmpty(author, "歌手不能为空");
        songPo.setAuthor(author);

        songPo.setFullName(name + "-" + author);

        String description = reqVo.getDescription();
        songPo.setDescription(description);

        String path = reqVo.getPath();
        checkNotEmpty(path, "文件路径不能为空");

        String reason = reqVo.getReason();
        songPo.setReason(reason);

        Integer sort = reqVo.getSort();
        songPo.setSort(sort);

        List<Integer> categoryIds = reqVo.getCategoryIds();
        if (isNotEmpty(categoryIds)) {
            List<CategoryPo> categoryPos = categoryRepository.findAllById(categoryIds);
            List<CategorySongPo> categorySongPos = categoryPos.stream().map(o -> {
                CategorySongPo categorySongPo = new CategorySongPo();
                categorySongPo.setSongPo(songPo);
                categorySongPo.setCategoryPo(o);
                return categorySongPo;
            }).collect(Collectors.toList());
            songPo.setSongCategoryPos(categorySongPos);
        }

        songPo.setUpdateTime(now());

        songRepository.save(songPo);
    }

    @Override
    public void deleteSong(List<Integer> ids) {
        checkNotEmpty(ids, "记录id列表不能为空");

        List<SongPo> songPos = songRepository.findAllById(ids);
        checkNotEmpty(songPos, "记录不存在,ids=" + ids);

        songPos.forEach(o -> {
            if (o.getDeleted()) {
                fail("记录已删除,id=" + o.getId());
            }
            o.setDeleted(true);
            o.setUpdateTime(now());
        });

        songRepository.saveAll(songPos);
    }
}
