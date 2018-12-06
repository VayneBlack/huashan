package com.guomin.api.wchat.vo;

import lombok.Data;
import java.io.Serializable;

@Data
public class UserHomeVo implements Serializable {
    private String userHeadPic;
    private String userNickName;
    private Integer userIsAuth;
    private Long userCredit;
    private Integer collectShopNum = 0;
    private Integer collectGoodsNum = 0;
}
