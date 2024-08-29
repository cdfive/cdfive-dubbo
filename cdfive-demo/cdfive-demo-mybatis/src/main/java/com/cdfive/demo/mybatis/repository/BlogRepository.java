package com.cdfive.demo.mybatis.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdfive.demo.mybatis.entity.Blog;
import com.cdfive.demo.mybatis.repository.mapper.BlogMapper;
import org.springframework.stereotype.Repository;

/**
 * @author cdfive
 */
@Repository
public class BlogRepository extends ServiceImpl<BlogMapper, Blog> {

}
