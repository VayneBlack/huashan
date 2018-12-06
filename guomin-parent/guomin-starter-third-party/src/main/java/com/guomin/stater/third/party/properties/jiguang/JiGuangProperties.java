package com.guomin.stater.third.party.properties.jiguang;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jiguang")
@Data
public class JiGuangProperties {
    public  String singleMsgUrl = "https://api.sms.jpush.cn/v1/codes";
    public  String codeValidateUrl = "https://api.sms.jpush.cn/v1/codes/";
    public  String appKey;
    public  String masterSecret;
}
