package com.github.demo.component.redis;


import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import redis.clients.util.SafeEncoder;

public class SafeEncodeSerializer implements RedisSerializer<String> {
    public SafeEncodeSerializer() {
    }

    public byte[] serialize(String t) throws SerializationException {
        return SafeEncoder.encode(t);
    }

    public String deserialize(byte[] bytes) throws SerializationException {
        return bytes != null && bytes.length != 0 ? SafeEncoder.encode(bytes) : null;
    }
}
