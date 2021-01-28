package com.cdfive.admin.controller;

import com.cdfive.common.api.ApiResponse;
import com.cdfive.common.base.AbstractController;
import com.cdfive.common.vo.page.BootstrapPageRespVo;
import com.cdfive.mp3.service.CategoryService;
import com.cdfive.mp3.vo.category.AddCategoryReqVo;
import com.cdfive.mp3.vo.category.FindCategoryDetailVo;
import com.cdfive.mp3.vo.category.QueryCategoryListPageReqVo;
import com.cdfive.mp3.vo.category.UpdateCategoryReqVo;
import com.cdfive.mp3.vo.song.FindSongDetailVo;
import com.cdfive.mp3.vo.song.QuerySongListPageReqVo;
import com.cdfive.mp3.vo.song.QuerySongListPageRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author cdfive
 */
@RequestMapping("/category")
@Controller
public class CategoryController extends AbstractController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list() {
        return new ModelAndView("mp3/category_list");
    }

    @ResponseBody
    @RequestMapping("/listData")
    public BootstrapPageRespVo<?> listData(QueryCategoryListPageReqVo reqVo) {
        return categoryService.queryCategoryListBootstrapPage(reqVo);
    }

    @GetMapping("/edit")
    public ModelAndView edit(Integer id) {
        ModelAndView mav = new ModelAndView("mp3/category_edit");
        FindCategoryDetailVo detailVo = categoryService.findCategoryDetail(id);
        mav.addObject("detailVo", detailVo);
        return mav;
    }

    @ResponseBody
    @RequestMapping("/add")
    public ApiResponse<?> add(AddCategoryReqVo reqVo) {
        categoryService.addCategory(reqVo);
        return succ();
    }

    @ResponseBody
    @RequestMapping("/update")
    public ApiResponse<?> update(UpdateCategoryReqVo reqVo) {
        categoryService.updateCategory(reqVo);
        return succ();
    }

    @ResponseBody
    @RequestMapping("/delete")
    public ApiResponse<?> delete(List<Integer> ids) {
        categoryService.deleteCategory(ids);
        return succ();
    }
}
