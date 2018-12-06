package com.guomin.api.wchat.service;

import com.guomin.api.wchat.dto.AliYunMsgDto;
import com.guomin.starter.commons.model.Response;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;

public interface AliYunService {
    Response sendLoginMsg(AliYunMsgDto record)throws Exception;
    Response fileUpload(MultipartFile file, HttpServletRequest request)throws Exception;
    Response fileList()throws Exception;
}
