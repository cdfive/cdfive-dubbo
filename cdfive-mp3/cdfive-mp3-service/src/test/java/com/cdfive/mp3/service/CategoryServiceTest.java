package com.cdfive.mp3.service;

import com.cdfive.mp3.Mp3Application;
import com.cdfive.mp3.vo.category.AddCategoryReqVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author cdfive
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Mp3Application.class)
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void testAddCategory() {
        AddCategoryReqVo reqVo = new AddCategoryReqVo();
//        reqVo.setName("aa");
        reqVo.setDescription("bb");
        Integer newId = null;
        try {
            newId = categoryService.addCategory(reqVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(newId);
    }
}