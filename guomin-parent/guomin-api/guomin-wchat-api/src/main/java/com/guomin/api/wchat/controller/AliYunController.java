package com.guomin.api.wchat.controller;

import com.guomin.api.wchat.dto.AliYunMsgDto;
import com.guomin.api.wchat.service.AliYunService;
import com.guomin.starter.commons.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("aliYun")
public class AliYunController {

    @Autowired
    AliYunService aliYunService;

    @PostMapping("/msg/login")
    public Response sendRegisterMsg(@RequestBody @Validated AliYunMsgDto record)throws Exception{
        return aliYunService.sendLoginMsg(record);
    }

    @PostMapping("/oss")
    public Response uploadFile(MultipartFile file, HttpServletRequest request)throws Exception{
        return aliYunService.fileUpload(file,request);
    }

    @GetMapping("/oss")
    public Response fileList()throws Exception{
        return aliYunService.fileList();
    }
}
