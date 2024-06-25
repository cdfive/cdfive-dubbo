package com.cdfive.demo.mybatis.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdfive.demo.mybatis.entity.Notice;
import com.cdfive.demo.mybatis.repository.mapper.NoticeMapper;
import org.springframework.stereotype.Repository;

/**
 * @author cdfive
 */
@Repository
public class NoticeRepository extends ServiceImpl<NoticeMapper, Notice> {

}
