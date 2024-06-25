package com.cdfive.demo.mybatis.controller;

import com.cdfive.demo.mybatis.base.PageRespVo;
import com.cdfive.demo.mybatis.service.NoticeService;
import com.cdfive.demo.mybatis.vo.PageQueryNoticeListReqVo;
import com.cdfive.demo.mybatis.vo.PageQueryNoticeListRespVo;
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
@RequestMapping("/notice")
@RestController
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping("/page")
    public PageRespVo<PageQueryNoticeListRespVo> pageQueryNoticeList(@RequestBody PageQueryNoticeListReqVo reqVo) {
        PageRespVo<PageQueryNoticeListRespVo> respVo = noticeService.pageQueryNoticeList(reqVo);
        return respVo;
    }
}
