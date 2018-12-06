package com.guomin.api.wchat.serviceImpl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.guomin.api.wchat.dto.AliYunMsgDto;
import com.guomin.api.wchat.service.AliYunService;
import com.guomin.starter.commons.exceptions.BussinessException;
import com.guomin.starter.commons.model.Response;
import com.guomin.starter.commons.model.aliyun.msg.AliMsgReq;
import com.guomin.starter.commons.utils.RespUtil;
import com.guomin.stater.third.party.aliyun.msg.AliMsgService;
import com.guomin.stater.third.party.aliyun.oss.AliOssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AliYunServiceImpl implements AliYunService {
    @Autowired
    AliOssService aliOssService;

    @Autowired
    AliMsgService aliMsgService;


    public Response sendLoginMsg(AliYunMsgDto record)throws Exception{
        String phone = record.getPhone();
        boolean isMobile = Validator.isMobile(phone);
        if (!isMobile){
            throw new BussinessException(-405,"手机号格式不对");
        }
        AliMsgReq aliMsgReq = new AliMsgReq();
        aliMsgReq.setPhone(phone);
        aliMsgService.sendMsg(aliMsgReq);
        return RespUtil.success();
    }

    public Response fileUpload(MultipartFile file, HttpServletRequest request)throws Exception{
        String path = request.getParameter("path");
        String type = request.getParameter("type");
        String id = request.getParameter("id");
        if (StrUtil.isEmpty(path)){
            throw new BussinessException(-405,"文件储存path必填");
        }
        if (StrUtil.isEmpty(type)){
            throw new BussinessException(-405,"文件储存type必填");
        }
        if (StrUtil.isEmpty(id)){
            throw new BussinessException(-405,"文件储存id必填");
        }
        String savePath = path+"/"+type+"/"+id;
        String result = aliOssService.uploadStr(savePath, file);
        return RespUtil.success(result);
    }

    public Response fileList()throws Exception{
        List<String> fileList = aliOssService.getFileListWithHostPrefix("");
        return RespUtil.success(fileList);
    }

}
