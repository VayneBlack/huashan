package com.guomin.api.wchat.config;

import io.lettuce.core.ClientOptions;
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


/**
 * 创建者:TcLi
 * 邮箱:254870497@qq.com
 * 创建时间:2018-10-26 09:55:45
 * 类描述: redis配置类
 *
 */
@Configuration
public class RedisConfig{
    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        ClientOptions.Builder clientBuilder = ClientOptions.builder();
        clientBuilder.pingBeforeActivateConnection(true);
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName("39.104.99.211");
        standaloneConfiguration.setPort(6379);
        standaloneConfiguration.setPassword(RedisPassword.of("li203204"));
        standaloneConfiguration.setDatabase(3);
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder = LettuceClientConfiguration.builder();
        builder.commandTimeout(Duration.ofMinutes(7));
        builder.shutdownTimeout(Duration.ofMinutes(8));
        builder.clientOptions(clientBuilder.build());
        LettuceConnectionFactory factory = new LettuceConnectionFactory(standaloneConfiguration,builder.build());
        return factory;
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}
