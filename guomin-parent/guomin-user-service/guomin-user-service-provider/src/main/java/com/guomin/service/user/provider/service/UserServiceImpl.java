package com.guomin.service.user.provider.service;

import cn.hutool.core.lang.Validator;
import com.alibaba.dubbo.config.annotation.Service;
import com.guomin.service.user.api.service.UserService;
import com.guomin.service.user.api.vo.UserHome;
import com.guomin.service.user.api.vo.UserListVo;
import com.guomin.service.user.provider.model.jpa.User;
import com.guomin.service.user.provider.model.mongo.UserWallet;
import com.guomin.service.user.provider.reponsitory.jpa.UserRep;
import com.guomin.service.user.provider.reponsitory.mongo.UserWalletRep;
import com.guomin.service.user.provider.reponsitory.neo4j.NUserRep;
import com.guomin.starter.commons.exceptions.BussinessException;
import com.guomin.starter.commons.model.Response;
import com.guomin.starter.commons.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import java.util.*;

@Service(group = "user-group",version = "1.0",timeout = 60*1000)
public class UserServiceImpl implements UserService {

    @Autowired
    UserRep userRep;

    @Autowired
    UserWalletRep userWalletRep;

    @Autowired
    NUserRep nUserRep;

    @Override
    public Response userLogin(String  userPhone) throws Exception {
        boolean isMobile = Validator.isMobile(userPhone);
        if (!isMobile){
            throw new BussinessException(-405,"手机号格式不对");
        }

        boolean existsPhone = userRep.existsByUserPhone(userPhone);

        User user;
        if (existsPhone){
            user = userRep.findByUserPhone(userPhone);
        }
        else {
            user = new User();
            user.setUserCreateTime(new Date());
            user.setUserPhone(userPhone);
            userRep.saveAndFlush(user);
            Long userId = user.getUserId();

            UserWallet userWallet = userWalletRep.findByUserId(userId);

            if (ObjectUtils.isEmpty(userWallet)){
                UserWallet saveWallet = new UserWallet();
                saveWallet.setUserId(userId);
                saveWallet.setWalletId(IdGenerator.nextId());
                userWalletRep.save(saveWallet);
            }
        }
        return RespUtil.success(user.getUserId());
    }

    @Override
    public UserHome userHome(Long userId) throws Exception {

        User user = userRep.findByUserId(userId);
        if (ObjectUtils.isEmpty(user)){
            throw new BussinessException(-405,"无该用户信息");
        }
        UserHome userHome = new UserHome();
        userHome.setUserIsAuth(user.getUserIsAuth());
        userHome.setUserNickName(user.getUserNickName());
        userHome.setUserHeadPic(user.getUserHeadPic());
        return userHome;
    }

    @Override
    @Transactional
    public Response delUser(Long userId) throws Exception {
        boolean exists = userRep.existsByUserId(userId);
        if (!exists){
            throw new BussinessException(-404,"用户不存在");
        }
        Long flag = userRep.deleteByUserId(userId);

        return flag>0 ? RespUtil.success():RespUtil.error(-405,"删除失败");
    }

    @Override
    public PageLimit<UserListVo> userList(Integer pageNum, Integer pageSize, Map<String, String> map) throws Exception {

        List<User> content = userRep.findAll(PageRequest.of(pageNum - 1, pageSize, Sort.by("userCreateTime").descending())).getContent();
        ArrayList<UserListVo> userListVos = new ArrayList<>();
        if (ObjectUtils.isEmpty(content)){
            PageLimit<UserListVo> result = new PageLimit(userListVos,0,pageNum,pageSize);
            return result;
        }
        for (User u:content) {
            UserListVo userListVo = new UserListVo();
            userListVo.setUserId(u.getUserId());
            userListVo.setUserSex(u.getUserSex());
            userListVo.setUserPhone(u.getUserPhone());
            userListVo.setUserCreateTime(u.getUserCreateTime());
            userListVos.add(userListVo);
        }
        PageLimit<UserListVo> result = new PageLimit(userListVos,userListVos.size(),pageNum,pageSize);
        return result;
    }
}
