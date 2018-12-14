package com.github.demo.component.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.util.ObjectUtils;

@Configuration
@EnableConfigurationProperties(value = {RedisProperties.class})
public class RedisConfig {

    @Autowired
    private RedisProperties redisProperties;

    public RedisConfig() {
    }

    @Bean
    @Qualifier("jedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(redisProperties.getHost());
        factory.setPort(redisProperties.getPort());
        if (!ObjectUtils.isEmpty(redisProperties.getPassword())) {
            factory.setPassword(redisProperties.getPassword());
        }
        factory.setDatabase(redisProperties.getDatabase());
        factory.setUsePool(true);
        factory.afterPropertiesSet();
        return factory;
    }

}
