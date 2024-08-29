package com.cdfive.demo.mybatis.service;

import com.cdfive.demo.mybatis.base.PageRespVo;
import com.cdfive.demo.mybatis.vo.PageQueryBlogListReqVo;
import com.cdfive.demo.mybatis.vo.PageQueryBlogListRespVo;

/**
 * @author cdfive
 */
public interface BlogService {

    PageRespVo<PageQueryBlogListRespVo> pageQueryBlogList(PageQueryBlogListReqVo reqVo);
}
