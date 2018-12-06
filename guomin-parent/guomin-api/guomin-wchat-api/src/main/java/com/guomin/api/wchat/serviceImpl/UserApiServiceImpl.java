package com.guomin.api.wchat.serviceImpl;

import cn.hutool.core.lang.Validator;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.guomin.api.wchat.dto.LoginCodeDto;
import com.guomin.api.wchat.dto.UserLoginDto;
import com.guomin.api.wchat.dto.UserWchartDto;
import com.guomin.api.wchat.service.UserApiService;
import com.guomin.api.wchat.vo.LoginCodeVo;
import com.guomin.api.wchat.vo.UserHomeVo;
import com.guomin.service.user.api.service.UserService;
import com.guomin.service.user.api.vo.UserHome;
import com.guomin.starter.commons.exceptions.BussinessException;
import com.guomin.starter.commons.model.Response;
import com.guomin.starter.commons.utils.DozerUtil;
import com.guomin.starter.commons.utils.RespUtil;
import com.guomin.stater.third.party.aliyun.msg.AliMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserApiServiceImpl implements UserApiService {

    private String appId = "wx0526a2a13ec58886";
    private String appSecret = "42cf6a2cb194c0428ad42e2bbd7c4e3e";
    private String getCodeUrl = "https://api.weixin.qq.com/sns/jscode2session?";

    @Reference(group = "user-group",version = "1.0")
    UserService userService;

    @Autowired
    AliMsgService aliMsgService;


    @Override
    public Response userLogin(UserLoginDto record) throws Exception {
        String userPhone = record.getUserPhone();
        String code = record.getCode();

        aliMsgService.checkMsg(userPhone, code);

        Response result = userService.userLogin(userPhone);
        return result;
    }

    @Override
    public Response userWchartLogin(UserWchartDto record) throws Exception {

        String userPhone = record.getUserPhone();
        boolean isMobile = Validator.isMobile(userPhone);
        if (!isMobile){
            throw new BussinessException(-405,"手机号格式不对");
        }
        Response response = userService.userLogin(userPhone);
        return response;
    }


    @Override
    public Response getUserCode(LoginCodeDto record) throws Exception {

        String code = record.getCode();

        String reqUrl = getCodeUrl + "appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code";

        HttpResponse execute = HttpRequest.get(reqUrl).execute();
        String body = execute.body();

        LoginCodeVo loginCodeVo = JSON.parseObject(body, LoginCodeVo.class);

        return RespUtil.success(loginCodeVo);
    }

    @Override
    public Response userHomeInfo(Long userId) throws Exception {
        UserHome userHome = userService.userHome(userId);

        UserHomeVo userHomeVo = new UserHomeVo();
        DozerUtil.copy(userHome,userHomeVo);
        return RespUtil.success(userHomeVo);
    }
}
