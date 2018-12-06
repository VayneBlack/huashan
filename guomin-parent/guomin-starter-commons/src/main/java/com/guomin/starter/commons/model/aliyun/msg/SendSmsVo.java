package com.guomin.starter.commons.model.aliyun.msg;

import lombok.Data;

import java.io.Serializable;

@Data
public class SendSmsVo implements Serializable {
    private String bizId;
}
