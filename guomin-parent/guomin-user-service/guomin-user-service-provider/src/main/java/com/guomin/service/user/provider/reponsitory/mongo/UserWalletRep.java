package com.guomin.service.user.provider.reponsitory.mongo;

import com.guomin.service.user.provider.model.mongo.UserWallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserWalletRep  extends MongoRepository<UserWallet,String> {
    UserWallet findByUserId(Long userId);
}
