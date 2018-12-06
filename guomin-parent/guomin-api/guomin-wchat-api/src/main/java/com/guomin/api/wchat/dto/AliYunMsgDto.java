package com.guomin.api.wchat.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AliYunMsgDto {
    @NotBlank(message = "手机号不能为空")
    private String phone;
}
