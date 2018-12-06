package com.guomin.service.user.api.vo;

import lombok.Data;
import java.io.Serializable;

@Data
public class UserHome implements Serializable {
    private String userHeadPic;
    private String userNickName;
    private Integer userIsAuth;
    private Long userCredit = 10000L;
}
