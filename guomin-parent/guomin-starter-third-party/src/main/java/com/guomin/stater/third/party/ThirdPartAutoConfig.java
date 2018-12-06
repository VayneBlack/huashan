package com.guomin.stater.third.party;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.guomin.starter.commons.CommonsAutoConfig;
import com.guomin.stater.third.party.aliyun.msg.AliMsgService;
import com.guomin.stater.third.party.aliyun.oss.AliOssService;
import com.guomin.stater.third.party.properties.aliyun.AliyunOssProperties;
import com.guomin.stater.third.party.properties.jiguang.JiGuangProperties;
import com.guomin.stater.third.party.jiguang.service.JiGuangService;
import com.guomin.stater.third.party.properties.aliyun.AliyunMsgProperties;
import io.lettuce.core.ClientOptions;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@AutoConfigureAfter(value = CommonsAutoConfig.class)
@EnableConfigurationProperties(value = {JiGuangProperties.class, AliyunMsgProperties.class,AliyunOssProperties.class})
public class ThirdPartAutoConfig {

    @Bean
    @ConditionalOnProperty(prefix = "jiguang",name = "enable",havingValue = "true")
    public JiGuangService jiGuangService(JiGuangProperties jiGuangProperties){
        JiGuangService jiGuangService = new JiGuangService();
        jiGuangService.setJiGuangProperties(jiGuangProperties);
        return jiGuangService;
    }

    @Bean
    public RedisConnectionFactory thirdRedisConnectionFactory(){
        ClientOptions.Builder clientBuilder = ClientOptions.builder();
        clientBuilder.pingBeforeActivateConnection(true);
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName("192.168.1.130");
        standaloneConfiguration.setPort(6379);
        standaloneConfiguration.setPassword(RedisPassword.of("li203204"));
        standaloneConfiguration.setDatabase(6);
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder = LettuceClientConfiguration.builder();
        builder.commandTimeout(Duration.ofMinutes(6));
        builder.shutdownTimeout(Duration.ofMinutes(6));
        builder.clientOptions(clientBuilder.build());
        LettuceConnectionFactory factory = new LettuceConnectionFactory(standaloneConfiguration,builder.build());
        return factory;
    }

    @Bean
    public RedisTemplate<String,Object> thirdRedisTemplate(){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setConnectionFactory(thirdRedisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    @ConditionalOnProperty(prefix = "aliyun.msg",name = "enable",havingValue = "true")
    public AliMsgService aliMsgService(AliyunMsgProperties aliyunMsgProperties) throws ClientException {

        final String product = "Dysmsapi";
        final String domain = "dysmsapi.aliyuncs.com";

        AliMsgService aliMsgService = new AliMsgService();
        aliMsgService.setAliyunMsgProperties(aliyunMsgProperties);
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliyunMsgProperties.getAccessKeyId(), aliyunMsgProperties.getAccessKeySecret());
        DefaultProfile.addEndpoint("cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        aliMsgService.setAcsClient(acsClient);
        aliMsgService.setRedisTemplate(thirdRedisTemplate());
        return aliMsgService;
    }


    @Bean
    @ConditionalOnProperty(prefix = "aliyun.oss",name = "enable",havingValue = "true")
    public AliOssService aliOssService(AliyunOssProperties aliyunOssProperties){
        AliOssService aliOssService = new AliOssService();
        aliOssService.setAliyunOssProperties(aliyunOssProperties);
        return aliOssService;
    }
}
