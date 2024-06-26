package com.cdfive.demo.mybatis.convert;

import com.cdfive.demo.mybatis.entity.Notice;
import com.cdfive.demo.mybatis.enums.NoticeStatus;
import com.cdfive.demo.mybatis.enums.NoticeType;
import com.cdfive.demo.mybatis.vo.AppQueryLatestNoticeRespVo;
import com.cdfive.demo.mybatis.vo.PageQueryNoticeListRespVo;
import com.cdfive.demo.mybatis.vo.QueryNoticeDetailRespVo;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author cdfive
 */

@SuppressWarnings("unused")
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface NoticeConvert {

    NoticeConvert INSTANCE = Mappers.getMapper(NoticeConvert.class);

    @Mapping(target = "statusText", source = "status", qualifiedByName = "noticeStatusText")
    @Mapping(target = "typeText", source = "type", qualifiedByName = "noticeTypeText")
    @Mapping(target = "effectiveStartTime", source = "effectiveStartTime", qualifiedByName = "formatLocalDateTime")
    @Mapping(target = "effectiveEndTime", source = "effectiveEndTime", qualifiedByName = "formatLocalDateTime")
    PageQueryNoticeListRespVo toPageQueryNoticeListRespVo(Notice notice);

    QueryNoticeDetailRespVo toQueryNoticeDetailRespVo(Notice notice);

    @Mapping(target = "typeText", source = "type", qualifiedByName = "noticeTypeText")
    AppQueryLatestNoticeRespVo toAppQueryLatestNoticeRespVo(Notice notice);

    @Named("noticeStatusText")
    default String noticeStatusText(NoticeStatus noticeStatus) {
        return noticeStatus == null ? null : noticeStatus.getDesc();
    }

    @Named("noticeTypeText")
    default String noticeTypeText(NoticeType noticeType) {
        return noticeType == null ? null : noticeType.getDesc();
    }

    @Named("formatLocalDateTime")
    default String formatLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
