package com.guomin.security.provider;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@DubboComponentScan(basePackages = {"com.guomin.security.provider.service"})
public class SecurityServerApp {
    public static void main(String[] args) {
        SpringApplication.run(SecurityServerApp.class,args);
    }
}
