package com.guomin.stater.third.party.jiguang.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ValidateCodeResp implements Serializable {
    private Boolean is_valid;
    private JiGuangError jiGuangError;
}
