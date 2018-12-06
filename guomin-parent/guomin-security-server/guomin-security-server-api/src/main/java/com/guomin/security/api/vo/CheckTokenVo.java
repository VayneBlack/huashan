package com.guomin.security.api.vo;

import lombok.Data;
import java.io.Serializable;

@Data
public class CheckTokenVo implements Serializable {
    private Integer code = 200;
    private String msg = "ok";
    private Boolean flag = true;
}
