package com.guomin.service.user.provider.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableMongoRepositories(basePackages = "com.guomin.service.user.provider.reponsitory.mongo")
public class MongoConfig extends AbstractMongoConfiguration {

    @Override
    public MongoClient mongoClient() {
        ServerAddress serverAddress = new ServerAddress("192.168.1.130",27017);
        MongoCredential mongoCredential = MongoCredential.createCredential("root", "admin", "li203204".toCharArray());

        MongoClientOptions options = MongoClientOptions.builder()
                .connectTimeout(600000)
                .connectionsPerHost(50)
                .maxConnectionIdleTime(600000)
                .maxWaitTime(600000)
                .socketTimeout(700000)
                .maxConnectionLifeTime(600000)
                .build();
//                .heartbeatConnectTimeout(10000);  //集群心跳检测

        MongoClient mongoClient = new MongoClient(serverAddress,mongoCredential,options);

        return mongoClient;
    }

    @Override
    protected String getDatabaseName() {
        return "gm";
    }


    @Bean
    public MongoTemplate mongoTemplate(){
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient(),"gm");
        return mongoTemplate;
    }
}

