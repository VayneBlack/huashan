package com.guomin.service.user.provider.model.neo4j;

import lombok.Data;
import org.neo4j.ogm.annotation.*;
import java.util.Date;

@NodeEntity(label = "User")
@Data
public class NUser {

    @Id
    @GeneratedValue
    private Long id;


    @Property("userId")
    @Index(unique = true)
    private Long userId;

    @Property("userCreateTime")
    private Date userCreateTime;
}
