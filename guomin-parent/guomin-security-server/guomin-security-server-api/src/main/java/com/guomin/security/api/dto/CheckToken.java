package com.guomin.security.api.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CheckToken implements Serializable {
    private String token;
}
