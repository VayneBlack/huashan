package com.guomin.security.api.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class FreshToken implements Serializable {
    private String id;
    private String name;
    private Boolean isMobile = false;
}
