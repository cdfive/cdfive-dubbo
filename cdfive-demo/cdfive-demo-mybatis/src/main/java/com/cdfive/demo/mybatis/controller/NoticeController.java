package com.cdfive.demo.mybatis.controller;

import com.cdfive.demo.mybatis.base.PageRespVo;
import com.cdfive.demo.mybatis.service.NoticeService;
import com.cdfive.demo.mybatis.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cdfive
 */
@Slf4j
@RequiredArgsConstructor
@Api(tags = "Demo >> 公告管理")
@RequestMapping("/notice")
@RestController
public class NoticeController {

    private final NoticeService noticeService;

    @ApiOperation(value = "分页查询公告列表")
    @PostMapping("/page")
    public PageRespVo<PageQueryNoticeListRespVo> page(@RequestBody PageQueryNoticeListReqVo reqVo) {
        PageRespVo<PageQueryNoticeListRespVo> respVo = noticeService.pageQueryNoticeList(reqVo);
        return respVo;
    }

    @ApiOperation(value = "保存公告")
    @PostMapping("/save")
    public SaveNoticeRespVo save(@RequestBody SaveNoticeReqVo reqVo) {
        SaveNoticeRespVo respVo = noticeService.saveNotice(reqVo);
        return respVo;
    }

    @ApiOperation(value = "查询公告详情")
    @PostMapping("/detail")
    public QueryNoticeDetailRespVo detail(@RequestBody QueryByIdReqVo reqVo) {
        QueryNoticeDetailRespVo respVo = noticeService.queryNoticeDetail(reqVo);
        return respVo;
    }

    @ApiOperation(value = "启用/禁用公告")
    @PostMapping("/enableOrDisable")
    public Boolean enableOrDisable(@RequestBody QueryByIdReqVo reqVo) {
        Boolean respVo = noticeService.enableOrDisableNotice(reqVo);
        return respVo;
    }

    @ApiOperation(value = "查询最新公告")
    @PostMapping("/latest")
    public AppQueryLatestNoticeRespVo latest(@RequestBody AppQueryLatestNoticeReqVo reqVo) {
        AppQueryLatestNoticeRespVo respVo = noticeService.appQueryLatestNotice(reqVo);
        return respVo;
    }

    @ApiOperation(value = "关闭公告")
    @PostMapping("/close")
    public AppCloseNoticeRespVo close(@RequestBody AppCloseNoticeReqVo reqVo) {
        AppCloseNoticeRespVo respVo = noticeService.appCloseNotice(reqVo);
        return respVo;
    }
}
