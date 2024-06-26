package com.cdfive.demo.mybatis.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdfive.demo.mybatis.entity.Notice;
import com.cdfive.demo.mybatis.enums.NoticeStatus;
import com.cdfive.demo.mybatis.repository.mapper.NoticeMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author cdfive
 */
@Repository
public class NoticeRepository extends ServiceImpl<NoticeMapper, Notice> {

    public List<Notice> queryCrossEffectiveTime(Long id, LocalDateTime startTime, LocalDateTime endTime) {
        return lambdaQuery()
                .eq(Notice::getStatus, NoticeStatus.ENABLE)
                .le(Notice::getEffectiveStartTime, endTime)
                .gt(Notice::getEffectiveEndTime, startTime)
                .ne(Notice::getId, id)
                .list();
    }

    public Notice queryLatest() {
        LocalDateTime now = LocalDateTime.now();
        return lambdaQuery().le(Notice::getEffectiveStartTime, now)
                .ge(Notice::getEffectiveEndTime, now)
                .eq(Notice::getStatus, NoticeStatus.ENABLE)
                .one();
    }

}
