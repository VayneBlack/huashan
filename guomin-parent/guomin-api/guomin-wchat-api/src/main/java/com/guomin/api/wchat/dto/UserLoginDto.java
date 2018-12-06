package com.guomin.api.wchat.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class UserLoginDto implements Serializable {
    private String userPhone;
    private String code;
}
