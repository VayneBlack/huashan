package com.guomin.starter.commons.model.aliyun.msg;

import lombok.Data;

import java.io.Serializable;

@Data
public class AliMsgReq implements Serializable {
    private String phone;
    private String signName = "国民平台";
    private String templateCode = "SMS_151905614";
}
