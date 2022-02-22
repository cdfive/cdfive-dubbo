package com.cdfive.admin.controller;

import com.cdfive.common.api.ApiResponse;
import com.cdfive.common.base.AbstractController;
import com.cdfive.common.vo.IntegerIdNameVo;
import com.cdfive.common.vo.page.BootstrapPageRespVo;
import com.cdfive.mp3.service.CategoryService;
import com.cdfive.mp3.service.SongService;
import com.cdfive.mp3.vo.song.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author cdfive
 */
@RequestMapping("/mp3")
@Controller
public class Mp3Controller extends AbstractController {

    @Autowired
    private SongService songService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list() {
        return new ModelAndView("mp3/mp3_list");
    }

    @ResponseBody
    @PostMapping("/list")
    public BootstrapPageRespVo<QuerySongListPageRespVo> listData(QuerySongListPageReqVo reqVo) {
        return songService.querySongListBootstrapPage(reqVo);
    }

    @GetMapping("/edit")
    public ModelAndView edit(Integer id) {
        ModelAndView mav = new ModelAndView("mp3/mp3_edit");

        FindSongDetailVo detailVo = songService.findSongDetail(id);
        mav.addObject("detailVo", detailVo);

        List<IntegerIdNameVo> categories = categoryService.findTopCategories();
        mav.addObject("categories", categories);

        return mav;
    }

    @ResponseBody
    @PostMapping("/add")
    public ApiResponse<?> add(AddSongReqVo reqVo) {
        songService.addSong(reqVo);
        return succ();
    }

    @ResponseBody
    @PostMapping("/update")
    public ApiResponse<?> add(UpdateSongReqVo reqVo) {
        songService.updateSong(reqVo);
        return succ();
    }

    @ResponseBody
    @PostMapping("/delete")
    public ApiResponse<?> delete(List<Integer> ids) {
        songService.deleteSong(ids);
        return succ();
    }
}
