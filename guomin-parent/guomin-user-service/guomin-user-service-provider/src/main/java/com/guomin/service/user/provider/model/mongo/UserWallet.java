package com.guomin.service.user.provider.model.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;

@Data
@Document(collection = "gm_user_wallet")
public class UserWallet implements Serializable {
    @Id
    private String id;

    @Field("wallet_id")
    private Long walletId;

    @Field("user_id")
    private Long userId;

    @Field("user_money")
    private String  userMoney = "OM28Y6Jm8/VkUhIkwQ2mlJb6ZhjVCYRoVull8spp6BmprRAk9t12YC30AnyMTJMf5BT7PksY7Ihm8SXUMpdfxpx6c0XREJwSSGhrhhg9FmjOrtinU7am4I9qNIbHsU/tb2ciGYl55etbgJbRHZfwJwYb2KJajgW9Vw9ParFGHDw=";
}
