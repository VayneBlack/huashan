package com.guomin.stater.third.party.jiguang.service;

import cn.hutool.core.lang.Validator;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.guomin.starter.commons.exceptions.BussinessException;
import com.guomin.stater.third.party.jiguang.model.JiGuangError;
import com.guomin.stater.third.party.jiguang.model.SingleMsg;
import com.guomin.stater.third.party.jiguang.model.SingleResp;
import com.guomin.stater.third.party.jiguang.model.ValidateCodeResp;
import com.guomin.stater.third.party.properties.jiguang.JiGuangProperties;
import org.springframework.util.ObjectUtils;
import java.util.HashMap;

public class JiGuangService {

    private JiGuangProperties jiGuangProperties;

    public Long sendSingleMsg(String mobile)throws Exception{

        boolean checkIsMobile1 = Validator.isMobile(mobile);
        if (!checkIsMobile1){
            throw new BussinessException(-405,"手机号格式不对");
        }
        SingleMsg singleMsg = new SingleMsg();
        singleMsg.setMobile(mobile);
        String response = HttpRequest.post(jiGuangProperties.singleMsgUrl)
                .basicAuth(jiGuangProperties.appKey, jiGuangProperties.masterSecret)
                .contentType("application/json")
                .body(JSON.toJSONString(singleMsg))
                .execute()
                .body();
        SingleResp singleResp = JSON.parseObject(response, SingleResp.class);
        JiGuangError error = singleResp.getJiGuangError();
        if (!ObjectUtils.isEmpty(error)){
            throw new BussinessException(-405,"验证码发送失败");
        }
        return singleResp.getMsg_id();
    }

    public Boolean validateCode(Long msgId,String code)throws Exception{
        HashMap<String, String> map = new HashMap<>();
        map.put("code",code);
        String response = HttpRequest.post(jiGuangProperties.codeValidateUrl + msgId + "/valid")
                .basicAuth(jiGuangProperties.appKey, jiGuangProperties.masterSecret)
                .contentType("application/json")
                .body(JSON.toJSONString(map))
                .execute()
                .body();

        ValidateCodeResp validateCodeResp = JSON.parseObject(response, ValidateCodeResp.class);
        Boolean is_valid = validateCodeResp.getIs_valid();
        if (!is_valid){
            String message = validateCodeResp.getJiGuangError().getMessage();
            if ("wrong msg_id".equals(message)){
                throw new BussinessException(-403,"错误消息ID");
            }
            if ("invalid code".equals(message)){
                throw new BussinessException(-405,"验证码错误");
            }
            if ("expired code".equals(message)){
                throw new BussinessException(-406,"验证码过期");
            }
            if ("verified code".equals(message)){
                throw new BussinessException(-407,"验证码已使用");
            }
        }
        return is_valid;
    }

    public void setJiGuangProperties(JiGuangProperties jiGuangProperties) {
        this.jiGuangProperties = jiGuangProperties;
    }
}
