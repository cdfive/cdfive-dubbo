package com.cdfive.demo.mybatis.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdfive.demo.mybatis.entity.Blog;
import com.cdfive.demo.mybatis.vo.PageQueryBlogListReqVo;
import com.cdfive.demo.mybatis.vo.PageQueryBlogListRespVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author cdfive
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {

    IPage<PageQueryBlogListRespVo> pageQuery(@Param("page") IPage<?> page, @Param("reqVo") PageQueryBlogListReqVo reqVo);
}
