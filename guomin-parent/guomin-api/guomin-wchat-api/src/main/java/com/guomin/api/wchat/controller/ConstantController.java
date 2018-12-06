package com.guomin.api.wchat.controller;

import com.guomin.api.wchat.service.ConstantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("constant")
public class ConstantController {
    @Autowired
    ConstantService constantService;

    @GetMapping("/captchaCode")
    public void getCaptchaCode(HttpServletResponse response)throws Exception{
        constantService.getCaptchaValidate(response);
    }
}
