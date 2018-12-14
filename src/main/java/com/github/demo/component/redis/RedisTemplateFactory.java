package com.github.demo.component.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class RedisTemplateFactory {

    @Qualifier("jedisConnectionFactory")
    @Autowired
    private JedisConnectionFactory connectionFactory;

    public RedisTemplateFactory() {
    }

    public <K, V> RedisTemplate<K, V> createTemplate() {
        RedisTemplate<K, V> template = new RedisTemplate<>();
        template.setKeySerializer(new SafeEncodeSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(this.connectionFactory);
        template.afterPropertiesSet();
        return template;
    }
}
