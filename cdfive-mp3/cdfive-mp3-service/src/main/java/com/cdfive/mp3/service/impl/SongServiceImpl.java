package com.cdfive.mp3.service.impl;

import com.cdfive.common.util.JpaPageUtil;
import com.cdfive.common.util.WebUtil;
import com.cdfive.common.vo.page.BootstrapPageRespVo;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.log.api.BizLogApi;
import com.cdfive.log.service.BizLogService;
import com.cdfive.mp3.entity.po.CategoryPo;
import com.cdfive.mp3.entity.po.CategorySongPo;
import com.cdfive.mp3.entity.po.SongPo;
import com.cdfive.mp3.repository.db.CategoryRepository;
import com.cdfive.mp3.repository.db.CategorySongRepository;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
@Slf4j
@Service("songService")
public class SongServiceImpl extends AbstractMp3Service implements SongService {

    private final LoadingCache<String, List<SongListVo>> songListByCategoryNameCache = CacheBuilder.newBuilder()
            .refreshAfterWrite(5, TimeUnit.SECONDS)
            .expireAfterWrite(30, TimeUnit.SECONDS)
            .maximumSize(10)
            .build(new CacheLoader<String, List<SongListVo>>() {
                @Override
                public List<SongListVo> load(String categoryName) throws Exception {
                    return songRepository.findSongListByCategoryName(categoryName);
                }
            });

    @Autowired
    private BizLogService bizLogService;

//    @Lazy
    @Autowired
    private BizLogApi bizLogApi;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategorySongRepository categorySongRepository;

    @Cacheable(value = "song", key = "'all'")
    @Transactional(readOnly = true)
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

//        bizLogApi.addBizLog("findAllSong", null, WebUtil.getIp());
        return respVo;
    }

//    @Cacheable(value = "song", key = "'random'")
    @Transactional(readOnly = true)
    @Override
    public List<SongListVo> findRandomSongList(Integer num) {
        log.info("songService=>findRandomSongList");
        checkCondition(num != null && num > 0, "数量应大于0");

        List<SongListVo> list = songRepository.findRandomSongList(num);
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer play(Integer id, String ip) {
        log.info("songService=>play");
        checkNotNull(id, "id不能为空");

//        bizLogService.addBizLog("播放mp3", id, ip);
//        bizLogApi.addBizLog("播放mp3", id, ip);

        SongPo songPo = songRepository.findById(id).orElseThrow(() -> exception("记录不存在,id=" + id));
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

    @Transactional(readOnly = true)
    @Override
    public PageRespVo<QuerySongListPageRespVo> querySongListPage(QuerySongListPageReqVo reqVo) {
        return JpaPageUtil.buildPage(reqVo, songRepository, new QuerySongSpecification(reqVo), QuerySongListPageTransformer.INSTANCE);
    }

    @Transactional(readOnly = true)
    @Override
    public BootstrapPageRespVo<QuerySongListPageRespVo> querySongListBootstrapPage(QuerySongListPageReqVo reqVo) {
        return JpaPageUtil.buildBootstrapPage(reqVo, songRepository, new QuerySongSpecification(reqVo), QuerySongListPageTransformer.INSTANCE);
    }

    @Transactional(readOnly = true)
    @Override
    public FindSongDetailVo findSongDetail(Integer id) {
        checkNotNull(id, "id不能为空");

        SongPo songPo = songRepository.findById(id).orElseThrow(() -> exception("记录不存在,id=" + id));

        FindSongDetailVo detailVo = new FindSongDetailVo();
        detailVo.setId(songPo.getId());
        detailVo.setName(songPo.getSongName());
        detailVo.setAuthor(songPo.getAuthor());
        detailVo.setDescription(songPo.getDescription());
        detailVo.setPath(songPo.getPath());
        detailVo.setDigit(songPo.getDigit());
        detailVo.setReason(songPo.getReason());
        detailVo.setPlayCount(songPo.getPlayCount());
        detailVo.setCreateTime(songPo.getCreateTime());
        detailVo.setUpdateTime(songPo.getUpdateTime());

        Integer categoryId = Optional.ofNullable(songPo.getSongCategoryPos()).map(o -> o.size() > 0 ? o.get(0) : null).map(o -> o.getCategoryPo().getId()).orElse(null);
        detailVo.setCategoryId(categoryId);
        return detailVo;
    }

    @Override
    public QuerySongDetailRespVo querySongDetail(QuerySongDetailReqVo reqVo) {
        checkNotNull(reqVo, "请求参数不能为空");

        Integer id = reqVo.getId();
        checkNotNull(id, "id不能为空");

        SongPo songPo = songRepository.findById(id).orElseThrow(() -> exception("记录不存在,id=" + id));

        QuerySongDetailRespVo respVo = new QuerySongDetailRespVo();
        List<CategorySongPo> songCategoryPos = songPo.getSongCategoryPos();
        if (songCategoryPos != null) {
            List<Integer> categoryIds = songPo.getSongCategoryPos().stream().map(o -> o.getCategoryPo().getId()).collect(Collectors.toList());
            respVo.setCategoryIds(categoryIds);
        }

        respVo.setId(songPo.getId());
        respVo.setName(songPo.getSongName());
        respVo.setAuthor(songPo.getAuthor());
        respVo.setDescription(songPo.getDescription());
        respVo.setPath(songPo.getPath());
        respVo.setDigit(songPo.getDigit());
        respVo.setReason(songPo.getReason());
        respVo.setPlayCount(songPo.getPlayCount());
        respVo.setSort(songPo.getSort());
        respVo.setCreateTime(songPo.getCreateTime());
        respVo.setUpdateTime(songPo.getUpdateTime());
        return respVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer addSong(AddSongReqVo reqVo) {
        checkNotNull(reqVo, "请求参数不能为空");

        SongPo songPo = new SongPo();

        String name = reqVo.getName();
        checkNotBlank(name, "歌名不能为空");
        songPo.setSongName(name);

        String author = reqVo.getAuthor();
        checkNotBlank(author, "歌手不能为空");
        songPo.setAuthor(author);

        songPo.setFullName(name + "-" + author);

        String description = reqVo.getDescription();
        songPo.setDescription(description);

        String path = reqVo.getPath();
        checkNotBlank(path, "文件路径不能为空");
        songPo.setPath(path);

        songPo.setDigit(name.length());

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
                categorySongPo.setCreateTime(now());

//                o.getCategorySongPos().add(categorySongPo);
//                categorySongPo.setId(128);
                return categorySongPo;
            }).collect(Collectors.toList());
            songPo.setSongCategoryPos(categorySongPos);
        }

        songPo.setCreateTime(now());
        songPo.setDeleted(false);

        songRepository.save(songPo);
//        categorySongRepository.saveAll(songPo.getSongCategoryPos());
        return songPo.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateSong(UpdateSongReqVo reqVo) {
        checkNotNull(reqVo, "请求参数不能为空");

        Integer id = reqVo.getId();
        checkNotNull(id, "记录id不能为空");

        SongPo songPo = songRepository.findById(id).orElseThrow(() -> exception("记录不存在,id=" + id));

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
        List<CategorySongPo> delPos = Collections.emptyList();
        if (isNotEmpty(categoryIds)) {
            List<CategorySongPo> songCategoryPos = songPo.getSongCategoryPos();

//            songCategoryPos.removeIf(o -> {
//                boolean result = !categoryIds.contains(o.getCategoryPo().getId());
//                System.out.println(categoryIds + "," + o.getCategoryPo().getId() + "," + result);
//                return result;
//            });

            delPos = songCategoryPos.stream().filter(o -> !categoryIds.contains(o.getCategoryPo().getId())).collect(Collectors.toList());
            System.out.println("delete size=" + delPos.size());

            songCategoryPos.removeAll(delPos);
            System.out.println("update size=" + songCategoryPos.size());
//            categorySongRepository.flush();

            Set<Integer> existCategoryIds = songCategoryPos.stream().map(o -> o.getCategoryPo().getId()).collect(Collectors.toSet());
            categoryIds.removeIf(o -> existCategoryIds.contains(o));
            System.out.println("add size=" + categoryIds.size());

            List<CategoryPo> categoryPos = categoryRepository.findAllById(categoryIds);
            List<CategorySongPo> categorySongPos = categoryPos.stream().map(o -> {
                CategorySongPo categorySongPo = new CategorySongPo();
                categorySongPo.setSongPo(songPo);
                categorySongPo.setCategoryPo(o);
                categorySongPo.setCreateTime(now());
//                categorySongPo.setId(128);

//                o.getCategorySongPos().add(categorySongPo);
                return categorySongPo;
            }).collect(Collectors.toList());
//            songPo.setSongCategoryPos(categorySongPos);
            songCategoryPos.addAll(categorySongPos);
        } else {
            delPos = songPo.getSongCategoryPos();
            songPo.setSongCategoryPos(Collections.emptyList());
            System.out.println("add size=0");
            System.out.println("update size=0");
            System.out.println("delete size=" + delPos.size());
        }

        songPo.setUpdateTime(now());

        categorySongRepository.deleteAll(delPos);
        songRepository.save(songPo);
    }

    @Override
    public void deleteSong(DeleteSongReqVo reqVo) {
        checkNotNull(reqVo, "请求参数不能为空");

        Integer id = reqVo.getId();
        checkNotNull(id, "记录id不能为空");

        SongPo songPo = songRepository.findById(id).orElseThrow(() -> exception("记录不存在,id=" + id));

        songPo.setDeleted(true);
        songPo.setUpdateTime(now());
        songRepository.save(songPo);
    }

    @Transactional(rollbackFor = Exception.class)
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
