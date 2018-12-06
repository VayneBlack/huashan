package com.guomin.api.wchat.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserWchartDto implements Serializable {
    @NotBlank(message = "用户手机号不能为空")
    private String userPhone;
}
