package com.guomin.service.user.api.service;

import com.guomin.service.user.api.vo.UserHome;
import com.guomin.service.user.api.vo.UserListVo;
import com.guomin.starter.commons.model.Response;
import com.guomin.starter.commons.utils.PageLimit;
import java.util.Map;

public interface UserService {
    Response userLogin(String  userPhone)throws Exception;
    UserHome userHome(Long userId)throws Exception;
    Response delUser(Long userId)throws Exception;
    PageLimit<UserListVo> userList(Integer pageNum, Integer pageSize, Map<String,String> map)throws Exception;
}
