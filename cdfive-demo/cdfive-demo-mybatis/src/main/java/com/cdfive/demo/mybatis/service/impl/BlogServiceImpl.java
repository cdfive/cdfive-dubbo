package com.cdfive.demo.mybatis.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdfive.demo.mybatis.base.PageRespVo;
import com.cdfive.demo.mybatis.repository.BlogRepository;
import com.cdfive.demo.mybatis.service.BlogService;
import com.cdfive.demo.mybatis.vo.PageQueryBlogListReqVo;
import com.cdfive.demo.mybatis.vo.PageQueryBlogListRespVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author cdfive
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    @Override
    public PageRespVo<PageQueryBlogListRespVo> pageQueryBlogList(PageQueryBlogListReqVo reqVo) {
        IPage<PageQueryBlogListRespVo> page = blogRepository.getBaseMapper().pageQuery(Page.of(reqVo.getPageNum(), reqVo.getPageSize()), reqVo);
        return new PageRespVo<PageQueryBlogListRespVo>((int) page.getCurrent(), (int) page.getSize(), (int) page.getTotal(), page.getRecords());
    }
}
