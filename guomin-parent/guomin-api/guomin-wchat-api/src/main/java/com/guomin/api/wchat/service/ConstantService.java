package com.guomin.api.wchat.service;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Service
public class ConstantService {
    @Autowired
    RedisTemplate redisTemplate;

    public void getCaptchaValidate(HttpServletResponse response) throws Exception {
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(200,80,4,20);
        String code = captcha.getCode();
        ServletOutputStream outputStream = response.getOutputStream();
        captcha.write(outputStream);
        outputStream.close();
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(code.toUpperCase(),code,20, TimeUnit.SECONDS);
    }

    public Boolean checkCaptchaValidate(String code) throws Exception {
        Boolean flag = redisTemplate.hasKey(code.toUpperCase());
        if (flag){
            redisTemplate.delete(code);
        }
        return flag;
    }
}
