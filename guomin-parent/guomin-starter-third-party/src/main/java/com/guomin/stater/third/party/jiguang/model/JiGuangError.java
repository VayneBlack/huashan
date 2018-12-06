package com.guomin.stater.third.party.jiguang.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class JiGuangError implements Serializable {
    private String code;
    private String message;
}
