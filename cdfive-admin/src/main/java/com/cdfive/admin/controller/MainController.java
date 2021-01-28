package com.cdfive.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author cdfive
 */
@Slf4j
@Controller
public class MainController {

    @RequestMapping("/main")
    public ModelAndView main() {
        ModelAndView mav = new ModelAndView("main/main");
        return mav;
    }
}
