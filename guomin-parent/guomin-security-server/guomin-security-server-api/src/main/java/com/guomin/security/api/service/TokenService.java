package com.guomin.security.api.service;


import com.guomin.security.api.dto.CheckToken;
import com.guomin.security.api.dto.GenerateToken;
import com.guomin.security.api.vo.CheckTokenVo;
import com.guomin.security.api.vo.TokenVo;

public interface TokenService {
    TokenVo generateToken(GenerateToken  record)throws Exception;
    CheckTokenVo checkToken(CheckToken record)throws Exception;
    CheckTokenVo checkToken(String token)throws Exception;
    TokenVo freshToken(GenerateToken  record)throws Exception;
}
