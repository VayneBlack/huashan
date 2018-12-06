package cn.likepeng.tm.manager.service;

import cn.likepeng.tm.model.TxServer;
import cn.likepeng.tm.model.TxState;

/**
 * create by lorne on 2017/11/11
 */
public interface MicroService {

    String  tmKey = "tx-manager";

    TxServer getServer();

    TxState getState();
}
