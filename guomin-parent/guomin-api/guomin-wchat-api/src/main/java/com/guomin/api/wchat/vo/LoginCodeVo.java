package com.guomin.api.wchat.vo;

import lombok.Data;
import java.io.Serializable;

@Data
public class LoginCodeVo implements Serializable {
    private String session_key;
    private String openid;
}
