package cn.likepeng.tm.redis;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.time.Duration;

/**
 * Created by lorne on 2017/7/5.
 */

@Configuration
public class RedisConfig {

    @Bean
    @ConfigurationProperties(prefix = "lcn.redis")
    public CustomRedisProperties customRedisProperties(){
        return new CustomRedisProperties();
    }


    @Bean
    public RedisConnectionFactory redisConnectionFactory(){

        CustomRedisProperties redisProperties = customRedisProperties();

        ClientOptions.Builder clientBuilder = ClientOptions.builder();

        SocketOptions socketOptions = SocketOptions.builder()
                .keepAlive(true)
                .connectTimeout(Duration.ofMinutes(2))
                .build();

        clientBuilder.autoReconnect(true);
        clientBuilder.socketOptions(socketOptions);
        clientBuilder.pingBeforeActivateConnection(true);

        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName(redisProperties.getHost());
        standaloneConfiguration.setPort(redisProperties.getPort());

        if (!StringUtils.isEmpty(redisProperties.getPassword())){
            standaloneConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
        }

        standaloneConfiguration.setDatabase(redisProperties.getDataBase());
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder = LettuceClientConfiguration.builder();
        builder.commandTimeout(Duration.ofMinutes(2));
        builder.shutdownTimeout(Duration.ofMinutes(2));
        builder.clientOptions(clientBuilder.build());

        LettuceConnectionFactory factory = new LettuceConnectionFactory(standaloneConfiguration,builder.build());
        return factory;

    }

    @Bean
    public RedisTemplate<?, ?> getRedisTemplate() {
        return new StringRedisTemplate(redisConnectionFactory());
    }
}
