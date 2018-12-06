package cn.likepeng.tm.api.service.impl;

import cn.likepeng.tm.api.service.ApiModelService;
import cn.likepeng.tm.manager.ModelInfoManager;
import cn.likepeng.tm.model.ModelInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * create by lorne on 2017/11/13
 */
@Service
public class ApiModelServiceImpl implements ApiModelService {


    @Override
    public List<ModelInfo> onlines() {
        return ModelInfoManager.getInstance().getOnlines();
    }


}
