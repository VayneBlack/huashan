package com.guomin.stater.third.party.properties.aliyun;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aliyun.msg")
@Data
public class AliyunMsgProperties {
    private String accessKeyId;
    private String accessKeySecret;
}
