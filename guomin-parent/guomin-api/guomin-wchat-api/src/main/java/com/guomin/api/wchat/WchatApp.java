package com.guomin.api.wchat;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@DubboComponentScan(basePackages = {"com.guomin.api.wchat.serviceImpl"})
public class WchatApp {
    public static void main(String[] args) {
        SpringApplication.run(WchatApp.class,args);
    }
}
