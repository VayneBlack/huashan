package com.guomin.stater.third.party.aliyun.msg;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.guomin.starter.commons.exceptions.BussinessException;
import com.guomin.starter.commons.model.aliyun.msg.AliMsgReq;
import com.guomin.stater.third.party.properties.aliyun.AliyunMsgProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class AliMsgService {
    private AliyunMsgProperties aliyunMsgProperties;
    private IAcsClient acsClient;

    private RedisTemplate redisTemplate;

    public void sendMsg(AliMsgReq record) throws Exception {
        SendSmsRequest request = new SendSmsRequest();
        String numbers = RandomUtil.randomNumbers(6);

        Boolean hasKey = redisTemplate.hasKey(record.getPhone());
        if (hasKey){
            Long expire = redisTemplate.getExpire(record.getPhone(), TimeUnit.SECONDS);
            if (expire>60*5){
                throw new BussinessException(-405,"验证码未过期");
            }
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("code",numbers);
        String code = JSON.toJSONString(map);

        request.setMethod(MethodType.POST);
        request.setPhoneNumbers(record.getPhone());
        request.setSignName(record.getSignName());
        request.setTemplateCode(record.getTemplateCode());
        request.setTemplateParam(code);
        try {
            acsClient.getAcsResponse(request);
        } catch (ClientException e) {
           throw new BussinessException(-405,e.getErrMsg());
        }

        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(record.getPhone(),numbers,10, TimeUnit.MINUTES);
    }

    public void checkMsg(String phone,String code) throws Exception {

        Boolean hasKey = redisTemplate.hasKey(phone);

        if (hasKey){
            Long expire = redisTemplate.getExpire(phone, TimeUnit.SECONDS);
            if (expire>0 && expire<60*5){
                throw new BussinessException(-405,"验证码已过期");
            }
            else if(expire>60*5){
                String c = (String) redisTemplate.opsForValue().get(phone);

                if (!code.equalsIgnoreCase(c)){
                    throw  new BussinessException(-405,"验证码不匹配");
                }
            }
        }else {
            throw new BussinessException(-405,"无效验证码");
        }
    }


    public void setAcsClient(IAcsClient acsClient) {
        this.acsClient = acsClient;
    }

    public void setAliyunMsgProperties(AliyunMsgProperties aliyunMsgProperties) {
        this.aliyunMsgProperties = aliyunMsgProperties;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
