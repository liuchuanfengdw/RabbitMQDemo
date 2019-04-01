package com.dw.helloworld.config;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * @Description: 自定义Redis序列化
 * @Author: DING WEI
 * @Date: 2019-04-01 21:54
 */
public class EntityRedisSerializer implements RedisSerializer<Object> {


    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if(o == null){
            return new byte[0];
        }
        return SerializeUtil.serialize(o);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return SerializeUtil.unserialize(bytes);
    }
}
