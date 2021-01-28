package com.cdfive.common.util;

import com.cdfive.common.vo.page.BootstrapPageRespVo;
import com.cdfive.common.vo.page.PageRespVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
public class PageUtil {

    public static <Vo, Po> PageRespVo<Vo> buildPage(Page<Po> page, Function<Po, Vo> transformer) {
        List<Po> list = page.getContent();
        List<Vo> data = list.stream().map(o -> transformer.apply(o)).collect(Collectors.toList());
        PageRespVo respVo = new PageRespVo(page.getNumber() + 1, page.getSize(), (int) page.getTotalElements(), data);
        return respVo;
    }

    public static <Vo, Po> BootstrapPageRespVo<Vo> buildBootstrapPage(Page<Po> page, Function<Po, Vo> transformer) {
        List<Po> list = page.getContent();
        List<Vo> data = list.stream().map(o -> transformer.apply(o)).collect(Collectors.toList());
        BootstrapPageRespVo respVo = new BootstrapPageRespVo((int) page.getTotalElements(), data);
        return respVo;
    }
}
