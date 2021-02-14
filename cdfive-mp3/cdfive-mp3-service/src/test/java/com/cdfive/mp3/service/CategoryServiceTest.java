package com.cdfive.mp3.service;

import com.cdfive.common.util.JacksonUtil;
import com.cdfive.common.vo.IntegerIdNameVo;
import com.cdfive.common.vo.page.BootstrapPageRespVo;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.mp3.BaseTest;
import com.cdfive.mp3.Mp3Application;
import com.cdfive.mp3.vo.category.AddCategoryReqVo;
import com.cdfive.mp3.vo.category.QueryCategoryListPageReqVo;
import com.cdfive.mp3.vo.category.QueryCategoryListPageRespVo;
import com.cdfive.mp3.vo.category.UpdateCategoryReqVo;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author cdfive
 */
public class CategoryServiceTest extends BaseTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void testQueryCategoryListPage() {
        QueryCategoryListPageReqVo reqVo = new QueryCategoryListPageReqVo();
        PageRespVo<QueryCategoryListPageRespVo> respVo = categoryService.queryCategoryListPage(reqVo);
        System.out.println(JacksonUtil.objToJson(respVo));
    }

    @Test
    public void testQueryCategoryListBootstrapPage() {
        QueryCategoryListPageReqVo reqVo = new QueryCategoryListPageReqVo();
        BootstrapPageRespVo<QueryCategoryListPageRespVo> respVo = categoryService.queryCategoryListBootstrapPage(reqVo);
        System.out.println(JacksonUtil.objToJson(respVo));
    }

    @Test
    public void testAddCategory() {
        AddCategoryReqVo reqVo = new AddCategoryReqVo();
        reqVo.setName("aa");
        reqVo.setDescription("11");
        Integer newId = null;
        try {
            newId = categoryService.addCategory(reqVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(newId);
        System.out.println("testAddCategory done");
    }

    @Test
    public void testUpdateCategory() {
        UpdateCategoryReqVo reqVo = new UpdateCategoryReqVo();
        reqVo.setId(15);
        reqVo.setName("bb");
        reqVo.setDescription("22");
        categoryService.updateCategory(reqVo);
        System.out.println("testDeleteCategory done");
    }

    @Test
    public void testDeleteCategory() {
        categoryService.deleteCategory(Lists.newArrayList(15));
        System.out.println("testDeleteCategory done");
    }

    @Test
    public void testFindTopCategories() {
        List<IntegerIdNameVo> result = categoryService.findTopCategories();
        System.out.println(JacksonUtil.objToJson(result));
    }
}