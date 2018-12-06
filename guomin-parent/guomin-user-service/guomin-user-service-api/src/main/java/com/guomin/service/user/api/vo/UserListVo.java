package com.guomin.service.user.api.vo;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserListVo implements Serializable {
    private Long userId;
    private String userName;
    private String userPhone;
    private Integer userSex;
    private BigDecimal userMoney;
    private Date userCreateTime;
}
