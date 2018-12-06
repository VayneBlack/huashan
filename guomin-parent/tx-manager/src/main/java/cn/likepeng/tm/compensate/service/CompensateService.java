package cn.likepeng.tm.compensate.service;

import cn.likepeng.tm.compensate.model.TransactionCompensateMsg;
import cn.likepeng.tm.compensate.model.TxModel;
import cn.likepeng.tm.model.ModelName;
import cn.likepeng.tm.netty.model.TxGroup;
import com.lorne.core.framework.exception.ServiceException;

import java.util.List;

/**
 * create by lorne on 2017/11/11
 */
public interface CompensateService {

    boolean saveCompensateMsg(TransactionCompensateMsg transactionCompensateMsg);

    List<ModelName> loadModelList();

    List<String> loadCompensateTimes(String model);

    List<TxModel> loadCompensateByModelAndTime(String path);

    void autoCompensate(String compensateKey, TransactionCompensateMsg transactionCompensateMsg);

    boolean executeCompensate(String key) throws ServiceException;

    void reloadCompensate(TxGroup txGroup);

    boolean hasCompensate();

    boolean delCompensate(String path);

    TxGroup  getCompensateByGroupId(String groupId);
}
