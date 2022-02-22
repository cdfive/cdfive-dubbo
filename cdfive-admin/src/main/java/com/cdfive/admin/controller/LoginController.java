package com.cdfive.admin.controller;

import com.cdfive.common.util.CaptchaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author cdfive
 */
@Slf4j
@Controller
public class LoginController {

    @RequestMapping(value = {"/", "/login"})
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }

    @RequestMapping("/loginProcess")
    public void loginProcess() {
        log.info("loginProcess start");
        log.info("loginProcess end");
    }

    @ResponseBody
    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome 2022";
    }

    @ResponseBody
    @RequestMapping("/whoami")
    public Object whoami() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @RequestMapping("/loginImageCheckCode")
    public void loginImageCheckCode(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        int width = 100;
        int height = 36;
        int fontSize = 5;
        String code = CaptchaUtil.generateCaptcha();
        log.info("登录图形验证码={}", code);
        CaptchaUtil.writeCaptcha(code, response, width, height, 25);
        session.setAttribute("sessionVerifyCode", code);
        session.setAttribute("sessionVerifyCodeTime", System.currentTimeMillis());
    }
}
