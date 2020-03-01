package com.cdfive.common.util;

import com.cdfive.common.vo.page.PageReqVo;
import com.cdfive.common.vo.page.PageRespVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
public class PageUtil {

    public static final Integer MAX_PAGE_SIZE = 50;

    public static <Vo, Po> PageRespVo<Vo> buildPage(PageReqVo reqVo, JpaSpecificationExecutor executor
            , Specification<Po> specification, Function<Po, Vo> transformer) {
        Integer pageNum = reqVo.getPageNum();
        if (pageNum <= 0) {
            throw new IllegalArgumentException("pageNum should greater than 0");
        }

        Integer pageSize = reqVo.getPageSize();
        if (pageSize <= 0) {
            throw new IllegalArgumentException("pageSize should greater than 0");
        }
        if (pageSize > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("pageSize should less than " + MAX_PAGE_SIZE);
        }

        /**
         * page in PageRequest start from 0, so here use pageNum - 1
         * @see {@link PageRequest#of(int, int)}
         */
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
        Page<Po> page = executor.findAll(specification, pageRequest);
        List<Po> list = page.getContent();
        List<Vo> data = list.stream().map(o -> transformer.apply(o)).collect(Collectors.toList());
        PageRespVo respVo = new PageRespVo(pageNum, pageSize, (int) page.getTotalElements(), data);
        return respVo;
    }
}
