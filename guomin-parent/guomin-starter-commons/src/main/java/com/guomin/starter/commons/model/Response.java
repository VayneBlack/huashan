package com.guomin.starter.commons.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class Response<T> implements Serializable {

    private Integer status;

    private String msg;

    private T data;
}