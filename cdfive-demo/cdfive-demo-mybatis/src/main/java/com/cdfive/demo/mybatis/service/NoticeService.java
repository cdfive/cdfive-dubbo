package com.cdfive.demo.mybatis.service;

import com.cdfive.demo.mybatis.base.PageRespVo;
import com.cdfive.demo.mybatis.vo.PageQueryNoticeListReqVo;
import com.cdfive.demo.mybatis.vo.PageQueryNoticeListRespVo;

/**
 * @author cdfive
 */
public interface NoticeService {

    PageRespVo<PageQueryNoticeListRespVo> pageQueryNoticeList(PageQueryNoticeListReqVo reqVo);
}
