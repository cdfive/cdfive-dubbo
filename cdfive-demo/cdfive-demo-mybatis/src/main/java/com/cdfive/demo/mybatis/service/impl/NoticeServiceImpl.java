package com.cdfive.demo.mybatis.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdfive.demo.mybatis.base.PageRespVo;
import com.cdfive.demo.mybatis.convert.NoticeConvert;
import com.cdfive.demo.mybatis.entity.Notice;
import com.cdfive.demo.mybatis.enums.NoticeStatus;
import com.cdfive.demo.mybatis.enums.NoticeType;
import com.cdfive.demo.mybatis.repository.NoticeRepository;
import com.cdfive.demo.mybatis.service.NoticeService;
import com.cdfive.demo.mybatis.vo.PageQueryNoticeListReqVo;
import com.cdfive.demo.mybatis.vo.PageQueryNoticeListRespVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    @Override
    public PageRespVo<PageQueryNoticeListRespVo> pageQueryNoticeList(PageQueryNoticeListReqVo reqVo) {
        if (reqVo == null) {
            return new PageRespVo<>();
        }

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
        NoticeStatus status = fromStringQuietly(NoticeStatus.class, reqVo.getStatus());
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
}
