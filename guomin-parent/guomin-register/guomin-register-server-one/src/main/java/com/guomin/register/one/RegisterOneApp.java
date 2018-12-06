package com.guomin.register.one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RegisterOneApp {
    public static void main(String[] args) {
        SpringApplication.run(RegisterOneApp.class,args);
    }
}
