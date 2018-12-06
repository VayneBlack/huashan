package com.guomin.service.user.provider.model.jpa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "gm_user")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class User  implements Serializable {
    /**	消息主键	*/
    @Id
    @GeneratedValue(generator = "idFactory")
    @GenericGenerator(name = "idFactory",strategy = "com.guomin.starter.commons.utils.IdGenerator")
    @Column(name = "user_id",columnDefinition = "bigint COMMENT  '用户主键'")
    private Long userId;

    @Column(name = "user_nick_name",columnDefinition = "varchar(255) COMMENT  '用户昵称'")
    private String userNickName = "用户昵称";

    @Column(name = "user_phone",columnDefinition = "varchar(255) COMMENT  '用户手机号'")
    private String userPhone;

    @Column(name = "user_head_pic",columnDefinition = "varchar(255) COMMENT  '用户头像'")
    private String userHeadPic = "https://f.guominpt.com/user/head/default/head.png";

    @Column(name = "user_login_password",columnDefinition = "varchar(255) COMMENT  '登录密码'")
    private String userLoginPassword = "123456";

    @Column(name = "user_sex",columnDefinition = "int COMMENT  '用户性别 0.保密 1.男 2.女'")
    private Integer userSex =0;

    @Column(name = "user_status",columnDefinition = "int COMMENT  '用户状态 0.冻结 1.正常'")
    private Integer userStatus = 1;

    @Column(name = "user_is_auth",columnDefinition = "int COMMENT  '用户是否实名认证 0.未认证 1.已认证'")
    private Integer userIsAuth = 0;

    @Column(name = "user_create_time",columnDefinition = "DATETIME COMMENT  '用户创建时间'")
    private Date userCreateTime;
}
