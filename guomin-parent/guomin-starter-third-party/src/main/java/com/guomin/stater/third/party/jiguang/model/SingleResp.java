package com.guomin.stater.third.party.jiguang.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class SingleResp implements Serializable {
    private Long msg_id;
    private JiGuangError jiGuangError;
}
