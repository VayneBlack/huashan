package com.guomin.service.user.provider.config;


import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jRepositories(basePackages = "com.guomin.service.user.provider.reponsitory.neo4j")
@EnableTransactionManagement
public class Neo4jConfig {

    @Bean
    public org.neo4j.ogm.config.Configuration configuration(){
        org.neo4j.ogm.config.Configuration  configuration= new org.neo4j.ogm.config.Configuration.Builder()
                .uri("bolt://192.168.1.130")
                .credentials("neo4j","li203204")
                .connectionPoolSize(50000)
                .connectionLivenessCheckTimeout(50)
                .build();
        return configuration;
    }

    @Bean
    public SessionFactory sessionFactory() {
        return new SessionFactory(configuration(), "com.guomin.service.user.provider.model.neo4j");
    }

    @Bean(name = "neo4jTransactionManager")
    public PlatformTransactionManager neo4jTransactionManager() {
        return new Neo4jTransactionManager(sessionFactory());
    }
}
