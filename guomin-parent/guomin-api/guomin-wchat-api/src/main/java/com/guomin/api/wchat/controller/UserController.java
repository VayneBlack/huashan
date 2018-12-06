package com.guomin.api.wchat.controller;

import com.guomin.api.wchat.dto.LoginCodeDto;
import com.guomin.api.wchat.dto.UserLoginDto;
import com.guomin.api.wchat.dto.UserWchartDto;
import com.guomin.api.wchat.service.UserApiService;
import com.guomin.starter.commons.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController{

    @Autowired
    UserApiService userApiService;

    @PostMapping("/login")
    public Response userLogin(@RequestBody UserLoginDto record) throws Exception {
        return userApiService.userLogin(record);
    }

    @PostMapping("/login/code")
    public Response getUserCode(@RequestBody LoginCodeDto record) throws Exception {
        return userApiService.getUserCode(record);
    }


    @PostMapping("/wchart/login")
    public Response userWchatLogin(@RequestBody @Validated UserWchartDto record) throws Exception {
        return userApiService.userWchartLogin(record);
    }

    @GetMapping("/home/{userId}")
    public Response userHomeInfo(@PathVariable Long userId) throws Exception {
        return userApiService.userHomeInfo(userId);
    }
}
