package cn.likepeng.tm.netty.service.impl;

import com.alibaba.fastjson.JSONObject;
import cn.likepeng.tm.manager.service.TxManagerService;
import cn.likepeng.tm.netty.model.TxGroup;
import cn.likepeng.tm.netty.service.IActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 创建事务组
 * create by lorne on 2017/11/11
 */
@Service(value = "cg")
public class ActionCGServiceImpl implements IActionService{


    @Autowired
    private TxManagerService txManagerService;

    @Override
    public String execute(String channelAddress, String key, JSONObject params ) {
        String res = "";
        String groupId = params.getString("g");
        TxGroup txGroup = txManagerService.createTransactionGroup(groupId);
        if(txGroup!=null) {
            txGroup.setNowTime(System.currentTimeMillis());
            res = txGroup.toJsonString(false);
        }else {
            res = "";
        }
        return res;
    }
}
