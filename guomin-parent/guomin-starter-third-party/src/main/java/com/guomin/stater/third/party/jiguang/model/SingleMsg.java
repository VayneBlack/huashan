package com.guomin.stater.third.party.jiguang.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SingleMsg implements Serializable {
    private String mobile;
    private Integer sign_id;
    private Integer temp_id = 1;

//    private Map<String,String> temp_para;
}
