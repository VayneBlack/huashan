package com.guomin.api.wchat.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginCodeDto implements Serializable {
    private String code;
}
