package com.cdfive.demo.mybatis.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdfive.demo.mybatis.entity.Category;
import com.cdfive.demo.mybatis.repository.mapper.CategoryMapper;
import org.springframework.stereotype.Repository;

/**
 * @author cdfive
 */
@Repository
public class CategoryRepository extends ServiceImpl<CategoryMapper, Category> {

}
