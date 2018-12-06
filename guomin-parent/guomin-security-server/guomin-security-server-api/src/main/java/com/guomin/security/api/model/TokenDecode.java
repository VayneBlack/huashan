package com.guomin.security.api.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class TokenDecode implements Serializable {
    private String iss;
    private String sub;
    private String jti;
    private Long exp;
}
