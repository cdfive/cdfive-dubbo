package com.cdfive.demo.mybatis.controller;

import com.cdfive.demo.mybatis.base.PageRespVo;
import com.cdfive.demo.mybatis.service.BlogService;
import com.cdfive.demo.mybatis.vo.PageQueryBlogListReqVo;
import com.cdfive.demo.mybatis.vo.PageQueryBlogListRespVo;
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
@RequestMapping("/blog")
@RestController
public class BlogController {

    private final BlogService blogService;

    @PostMapping("/page")
    public PageRespVo<PageQueryBlogListRespVo> page(@RequestBody PageQueryBlogListReqVo reqVo) {
        PageRespVo<PageQueryBlogListRespVo> respVo = blogService.pageQueryBlogList(reqVo);
        return respVo;
    }
}
