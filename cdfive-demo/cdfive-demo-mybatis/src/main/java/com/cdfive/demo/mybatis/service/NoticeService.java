package com.cdfive.demo.mybatis.service;

import com.cdfive.demo.mybatis.base.PageRespVo;
import com.cdfive.demo.mybatis.vo.*;

/**
 * @author cdfive
 */
public interface NoticeService {

    /**
     * 分页查询公告列表
     */
    PageRespVo<PageQueryNoticeListRespVo> pageQueryNoticeList(PageQueryNoticeListReqVo reqVo);

    /**
     * 保存公告
     */
    SaveNoticeRespVo saveNotice(SaveNoticeReqVo reqVo);

    /**
     * 查询公告详情
     */
    QueryNoticeDetailRespVo queryNoticeDetail(QueryByIdReqVo reqVo);

    /**
     * 启用或禁用公告
     */
    Boolean enableOrDisableNotice(QueryByIdReqVo reqVo);

    /**
     * APP端查询最新公告
     */
    AppQueryLatestNoticeRespVo appQueryLatestNotice(AppQueryLatestNoticeReqVo reqVo);

    /**
     * APP端关闭公告
     */
    AppCloseNoticeRespVo appCloseNotice(AppCloseNoticeReqVo reqVo);
}
