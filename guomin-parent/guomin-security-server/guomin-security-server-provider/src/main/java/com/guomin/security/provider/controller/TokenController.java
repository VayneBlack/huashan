package com.guomin.security.provider.controller;

import com.guomin.security.api.dto.CheckToken;
import com.guomin.security.api.dto.GenerateToken;
import com.guomin.security.api.service.TokenService;
import com.guomin.security.api.vo.CheckTokenVo;
import com.guomin.starter.commons.model.Response;
import com.guomin.starter.commons.utils.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("token")
public class TokenController {

    @Autowired
    TokenService tokenService;

    @PostMapping("")
    public Response generateToken(@RequestBody GenerateToken  record) throws Exception {
        return RespUtil.success(tokenService.generateToken(record));
    }

    @PostMapping("/check")
    public CheckTokenVo checkToken(@RequestBody CheckToken record) throws Exception {
        return tokenService.checkToken(record);
    }

    @PostMapping("/freshToken")
    public Response flushToken(@RequestBody GenerateToken  record) throws Exception {
        return RespUtil.success(tokenService.freshToken(record));
    }
}
