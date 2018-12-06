package com.guomin.api.wchat.service;


import com.guomin.api.wchat.dto.LoginCodeDto;
import com.guomin.api.wchat.dto.UserLoginDto;
import com.guomin.api.wchat.dto.UserWchartDto;
import com.guomin.starter.commons.model.Response;

public interface UserApiService {
    Response userLogin(UserLoginDto record)throws Exception;
    Response userWchartLogin(UserWchartDto record)throws Exception;
    Response getUserCode(LoginCodeDto record)throws Exception;
    Response userHomeInfo(Long userId)throws Exception;
}
