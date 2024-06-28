package com.cdfive.demo.mybatis.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdfive.demo.mybatis.base.PageRespVo;
import com.cdfive.demo.mybatis.convert.NoticeConvert;
import com.cdfive.demo.mybatis.entity.Notice;
import com.cdfive.demo.mybatis.enums.ClientType;
import com.cdfive.demo.mybatis.enums.NoticeScene;
import com.cdfive.demo.mybatis.enums.NoticeStatus;
import com.cdfive.demo.mybatis.enums.NoticeType;
import com.cdfive.demo.mybatis.repository.NoticeRepository;
import com.cdfive.demo.mybatis.service.NoticeService;
import com.cdfive.demo.mybatis.util.JsonUtil;
import com.cdfive.demo.mybatis.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {

    private static final int MAX_POPUP_WINDOW_TIMES = 9999;

    private static final String EFFECTIVE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String EFFECTIVE_TIME_CHECK_ERROR_MSG = "与“%s”公告生效时间重叠，相同时间段内只能启用一条公告！";

    private static final String NOTICE_LATEST_CACHE_KEY = "cjj:notice:latest";

    private static final String NOTICE_LATEST_USER_CACHE_KEY = "cjj:notice:latest:user:%s:%s";

    private final NoticeRepository noticeRepository;

    private final RedisTemplate<String, Serializable> redisTemplate;

    @Transactional(readOnly = true)
    @Override
    public PageRespVo<PageQueryNoticeListRespVo> pageQueryNoticeList(PageQueryNoticeListReqVo reqVo) {
        if (reqVo == null) {
            return new PageRespVo<>();
        }

        // Mock error
//        if (true) {
//            int i = 1 / 0;
//        }

        String idStr = reqVo.getId();
        Long id = null;
        if (StringUtils.isNotBlank(idStr)) {
            idStr = idStr.trim();
            if (!isLong(idStr)) {
                return new PageRespVo<>();
            }

            id = Long.parseLong(idStr);
        }

        String title = reqVo.getTitle();
//        NoticeStatus status = fromStringQuietly(NoticeStatus.class, reqVo.getStatus());
        NoticeStatus status = reqVo.getStatus();
        NoticeType type = fromStringQuietly(NoticeType.class, reqVo.getType());

        IPage<Notice> page = noticeRepository.lambdaQuery()
                .eq(id != null, Notice::getId, id)
                .like(StringUtils.isNotBlank(title), Notice::getTitle, (title != null ? title.trim() : null))
                .eq(status != null, Notice::getStatus, status)
                .eq(type != null, Notice::getType, type)
                .orderByDesc(Notice::getId)
                .page(Page.of(reqVo.getPageNum(), reqVo.getPageSize()));
        List<PageQueryNoticeListRespVo> data = page.getRecords().stream().map(o -> NoticeConvert.INSTANCE.toPageQueryNoticeListRespVo(o)).collect(Collectors.toList());
        return new PageRespVo<PageQueryNoticeListRespVo>((int) page.getCurrent(), (int) page.getSize(), (int) page.getTotal(), data);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SaveNoticeRespVo saveNotice(SaveNoticeReqVo reqVo) {
        Assert.notNull(reqVo, "请求参数不能为空");

        Notice notice;
        Long id = reqVo.getId();
        if (id == null) {
            notice = new Notice();
            // 新增时设置状态为新建
            notice.setStatus(NoticeStatus.NEW);
        } else {
            notice = noticeRepository.getById(id);
            Assert.notNull(notice, "公告不存在,公告id=" + id);
            Assert.isTrue(NoticeStatus.NEW.equals(notice.getStatus()), "新建状态下才能编辑");
        }

        String title = reqVo.getTitle();
        Assert.hasText(title, "公告标题不能为空");
        notice.setTitle(title);

        String strEffectiveStartTime = reqVo.getEffectiveStartTime();
        Assert.hasText(strEffectiveStartTime, "生效开始时间不能为空");
        LocalDateTime effectiveStartTime = parseEffectiveTime(strEffectiveStartTime);
        Assert.notNull(effectiveStartTime, "生效开始时间有误," + strEffectiveStartTime);
        notice.setEffectiveStartTime(effectiveStartTime);

        String strEffectiveEndTime = reqVo.getEffectiveEndTime();
        Assert.hasText(strEffectiveEndTime, "生效结束时间不能为空");
        LocalDateTime effectiveEndTime = parseEffectiveTime(strEffectiveEndTime);
        Assert.notNull(effectiveEndTime, "生效结束时间有误," + strEffectiveEndTime);
        notice.setEffectiveEndTime(effectiveEndTime);

        Assert.isTrue(effectiveStartTime.compareTo(effectiveEndTime) <= 0, "生效结束时间不能小于生效开始时间");
        Assert.isTrue(effectiveEndTime.compareTo(LocalDateTime.now()) > 0, "生效结束时间应大于当前时间");

        Set<String> strClientTypes = reqVo.getClientTypes();
        Assert.notEmpty(strClientTypes, "客户端类型集合不能为空");
        LinkedHashSet<ClientType> clientTypes = strClientTypes.stream().map(o -> fromStringQuietly(ClientType.class, o)).filter(Objects::nonNull).collect(Collectors.toCollection(LinkedHashSet::new));
        Assert.isTrue(strClientTypes.size() == clientTypes.size(), "客户端类型集合有误," + strClientTypes);
        notice.setClientTypes(clientTypes);

        Set<String> strScenes = reqVo.getScenes();
        Assert.notEmpty(strScenes, "场景集合不能为空");
        LinkedHashSet<NoticeScene> scenes = strScenes.stream().map(o -> fromStringQuietly(NoticeScene.class, o)).filter(Objects::nonNull).collect(Collectors.toCollection(LinkedHashSet::new));
        Assert.isTrue(strScenes.size() == scenes.size(), "场景集合有误," + strScenes);
        notice.setScenes(scenes);

        String strType = reqVo.getType();
        Assert.hasText(strType, "公告类型不能为空");
        NoticeType type = fromStringQuietly(NoticeType.class, strType);
        Assert.notNull(type, "公告类型有误," + strType);
        notice.setType(type);

        Integer popupWindowTimes = reqVo.getPopupWindowTimes();
        Assert.notNull(popupWindowTimes, "弹窗次数不能为空");
        Assert.isTrue(popupWindowTimes > 0 && popupWindowTimes < MAX_POPUP_WINDOW_TIMES, "弹窗次数限制范围:1-" + MAX_POPUP_WINDOW_TIMES);
        notice.setPopupWindowTimes(popupWindowTimes);

        String coverImage = reqVo.getCoverImage();
        notice.setCoverImage(coverImage);

        String extData = reqVo.getExtData();
        notice.setExtData(extData);

        if (NoticeType.TEXT.equals(type)) {
            // 如果是文本弹窗,验证公告内容必填
            String content = reqVo.getContent();
            Assert.hasText(content, "公告内容不能为空");
            notice.setContent(content);
        } else {
            // 图片公告设置内容为空
            notice.setContent(null);
        }

        noticeRepository.saveOrUpdate(notice);
        SaveNoticeRespVo respVo = new SaveNoticeRespVo();
        return respVo;
    }

    @Transactional(readOnly = true)
    @Override
    public QueryNoticeDetailRespVo queryNoticeDetail(QueryByIdReqVo reqVo) {
        Assert.notNull(reqVo, "请求参数不能为空");

        Long id = reqVo.getId();
        Assert.notNull(id, "公告id不能为空");

        Notice notice = noticeRepository.getById(id);
        Assert.notNull(notice, "公告不存在,公告id=" + id);

        QueryNoticeDetailRespVo respVo = NoticeConvert.INSTANCE.toQueryNoticeDetailRespVo(notice);
        return respVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean enableOrDisableNotice(QueryByIdReqVo reqVo) {
        Assert.notNull(reqVo, "请求参数不能为空");

        Long id = reqVo.getId();
        Assert.notNull(id, "公告id不能为空");

        Notice notice = noticeRepository.getById(id);
        Assert.notNull(notice, "公告不存在,公告id=" + id);

        NoticeStatus status = notice.getStatus();
        if (NoticeStatus.ENABLE.equals(status)) {
            notice.setStatus(NoticeStatus.DISABLE);
        } else {
            notice.setStatus(NoticeStatus.ENABLE);

            // 启用时,验证生效时间范围跟其它启用的公告生效时间范围不重叠
            List<Notice> existNotices = noticeRepository.queryCrossEffectiveTime(id, notice.getEffectiveStartTime(), notice.getEffectiveEndTime());
            if (CollectionUtils.isNotEmpty(existNotices)) {
                throw new RuntimeException(String.format(EFFECTIVE_TIME_CHECK_ERROR_MSG, existNotices.get(0).getTitle()));
            }
        }

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                queryLatestAndCache();
            }
        });

        boolean result = noticeRepository.updateById(notice);
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public AppQueryLatestNoticeRespVo appQueryLatestNotice(AppQueryLatestNoticeReqVo reqVo) {
        Assert.notNull(reqVo, "请求参数不能为空");

        AppQueryLatestNoticeRespVo respVo = queryLatest();
        if (respVo == null) {
            if (log.isDebugEnabled()) {
                log.debug("当前没有公告,reqVo={}", JsonUtil.objToStr(reqVo));
            }
            return respVo;
        }

        Long memberId = reqVo.getMemberId();
        if (memberId == null) {
            if (log.isDebugEnabled()) {
                log.debug("未登录,返回公告,respVo={},reqVo={}", JsonUtil.objToStr(respVo), JsonUtil.objToStr(reqVo));
            }
            return respVo;
        }

        Integer popupWindowTimes = respVo.getPopupWindowTimes();
        Long noticeId = respVo.getId();
        String cacheKey = String.format(NOTICE_LATEST_USER_CACHE_KEY, noticeId, memberId);
        Serializable times = redisTemplate.opsForValue().get(cacheKey);
        if (times != null) {
            int intTimmes = Integer.parseInt(times.toString());
            if (intTimmes >= popupWindowTimes) {
                if (log.isDebugEnabled()) {
                    log.debug("超过最大弹窗次数,popupWindowTimes={},times={},reqVo={}", popupWindowTimes, times, JsonUtil.objToStr(reqVo));
                }
                return null;
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("已登录,返回公告,respVo={},reqVo={}", JsonUtil.objToStr(respVo), JsonUtil.objToStr(reqVo));
        }
        return respVo;
    }

    @Transactional(readOnly = true)
    @Override
    public AppCloseNoticeRespVo appCloseNotice(AppCloseNoticeReqVo reqVo) {
        Assert.notNull(reqVo, "请求参数不能为空");

        Long memberId = reqVo.getMemberId();
        Assert.notNull(memberId, "用户id不能为空");

        Long noticeId = reqVo.getNoticeId();
        Assert.notNull(noticeId, "公告id不能为空");

        AppQueryLatestNoticeRespVo latestNoticeVo = queryLatest();
        if (latestNoticeVo == null) {
            if (log.isInfoEnabled()) {
                log.info("关闭公告,当前没有公告,reqVo={}", JsonUtil.objToStr(reqVo));
            }
            return null;
        }

        if (!noticeId.equals(latestNoticeVo.getId())) {
            if (log.isInfoEnabled()) {
                log.info("关闭公告,不是当前公告,公告id={},当前公告id={},reqVo={}", noticeId, latestNoticeVo.getId(), JsonUtil.objToStr(reqVo));
            }
            return null;
        }

        String cacheKey = String.format(NOTICE_LATEST_USER_CACHE_KEY, noticeId, memberId);
        Long times = redisTemplate.opsForValue().increment(cacheKey, 1L);
        // 设置缓存过期时间为活动结束时间
        redisTemplate.expireAt(cacheKey, Date.from(latestNoticeVo.getEffectiveEndTime().atZone(ZoneId.systemDefault()).toInstant()));

        if (log.isInfoEnabled()) {
            log.info("关闭公告成功,公告id={},用户id={},次数={}", noticeId, memberId, times);
        }
        AppCloseNoticeRespVo respVo = new AppCloseNoticeRespVo();
        return respVo;
    }

    private AppQueryLatestNoticeRespVo queryLatest() {
        AppQueryLatestNoticeRespVo respVo = null;
        Serializable serializable = redisTemplate.opsForValue().get(NOTICE_LATEST_CACHE_KEY);
        if (serializable == null) {
            log.debug("当前没有公告");
            return null;
        }

        Notice notice = JsonUtil.strToObj(serializable.toString(), Notice.class);
        if (notice == null) {
            log.debug("当前没有公告");
            return null;
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime effectiveStartTime = notice.getEffectiveStartTime();
        if (effectiveStartTime.compareTo(now) > 0) {
            log.debug("公告未到生效开始时间");
            return null;
        }

        LocalDateTime effectiveEndTime = notice.getEffectiveEndTime();
        if (effectiveStartTime.compareTo(now) <= 0 && effectiveEndTime.compareTo(now) >= 0) {
            return NoticeConvert.INSTANCE.toAppQueryLatestNoticeRespVo(notice);
        }

        log.info("公告已过期,公告id={},标题={},生效开始时间={},生效结束时间={}", notice.getId(), notice.getTitle(), formatEffectiveTime(effectiveStartTime), formatEffectiveTime(effectiveEndTime));

        notice = queryLatestAndCache();
        if (notice == null) {
            log.info("已过期且没有最新公告");
            return null;
        }

        effectiveStartTime = notice.getEffectiveStartTime();
        if (effectiveStartTime.compareTo(now) > 0) {
            log.info("已过期且最新公告未到生效开始时间,公告id={},标题={},生效开始时间={},生效结束时间={}", notice.getId(), notice.getTitle(), formatEffectiveTime(notice.getEffectiveStartTime()), formatEffectiveTime(notice.getEffectiveEndTime()));
            return null;
        }

        effectiveEndTime = notice.getEffectiveEndTime();
        if (effectiveStartTime.compareTo(now) <= 0 && effectiveEndTime.compareTo(now) >= 0) {
            log.info("已过期且最新公告生效中,公告id={},标题={},生效开始时间={},生效结束时间={}", notice.getId(), notice.getTitle(), formatEffectiveTime(notice.getEffectiveStartTime()), formatEffectiveTime(notice.getEffectiveEndTime()));
            return NoticeConvert.INSTANCE.toAppQueryLatestNoticeRespVo(notice);
        }

        log.error("已过期且最新公告不合法,公告id={},标题={}", notice.getId(), notice.getTitle());
        return null;
    }

    private Notice queryLatestAndCache() {
        Notice notice = noticeRepository.queryLatest();
        if (notice == null) {
            log.info("查询最新公告,为空");
            redisTemplate.delete(NOTICE_LATEST_CACHE_KEY);
            return null;
        }

        log.info("查询最新公告,公告id={},标题={},生效开始时间={},生效结束时间={}", notice.getId(), notice.getTitle(), formatEffectiveTime(notice.getEffectiveStartTime()), formatEffectiveTime(notice.getEffectiveEndTime()));
        redisTemplate.opsForValue().set(NOTICE_LATEST_CACHE_KEY, JsonUtil.objToStr(notice));
        return notice;
    }

    private <T extends Enum<T>> T fromStringQuietly(Class<T> clazz, String str) {
        try {
            return Enum.valueOf(clazz, str);
        } catch (Exception e) {
            // Ignore
            return null;
        }
    }

    private boolean isLong(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            // Ignore
            return false;
        }
    }

    private LocalDateTime parseEffectiveTime(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }

        try {
            return LocalDateTime.parse(str, DateTimeFormatter.ofPattern(EFFECTIVE_TIME_FORMAT));
        } catch (Exception e) {
            // Ignore
            return null;
        }
    }

    private String formatEffectiveTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }

        try {
            return DateTimeFormatter.ofPattern(EFFECTIVE_TIME_FORMAT).format(localDateTime);
        } catch (Exception e) {
            // Ignore
            return null;
        }
    }
}
