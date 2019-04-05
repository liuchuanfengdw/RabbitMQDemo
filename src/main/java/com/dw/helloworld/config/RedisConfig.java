package com.dw.helloworld.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Description: Redis配置类
 * @Author: DING WEI
 * @Date: 2019-04-01 21:26
 */
@Configuration
public class RedisConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.pool.max-active}")
    private int maxActive;
    @Value("${spring.redis.pool.max-wait}")
    private int maxWait;
    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.pool.min-idle}")
    private int minIdle;
    @Value("${spring.redis.database}")
    private int database;
    @Value("${spring.redis.timeout}")
    private int timeout;

    /**
     * JedisPoolConfig配置
     * @Author: DING WEI
     * @Date: 2019-04-01 21:43
     * @Version: 1.0
     */
    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig(){
        logger.info("【初始化JedisPoolConfig】");
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //  连接池最大连接数（使用负值表示没有限制）
        jedisPoolConfig.setMaxTotal(maxActive);
        // 连接池最大阻塞等待时间（使用负值表示没有限制）
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        // 连接池中的最大空闲连接
        jedisPoolConfig.setMaxIdle(maxIdle);
        // 连接池中的最小空闲连接
        jedisPoolConfig.setMinIdle(minIdle);
        return jedisPoolConfig;
    }

    /**
     * jedisConnectionFactory配置类
     * @Author: DING WEI
     * @Date: 2019-04-01 21:48
     * @Version: 1.0
     */
    @Bean(name = "jedisConnectionFactory")
    public RedisConnectionFactory jedisConnectionFactory(@Qualifier(value = "jedisPoolConfig") JedisPoolConfig jedisPoolConfig){
        logger.info("【初始化RedisConnectionFactory】");
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(jedisPoolConfig);
        connectionFactory.setHostName(host);
        connectionFactory.setPort(port);
        connectionFactory.setPassword(password);
        connectionFactory.setDatabase(database);
        return connectionFactory;
    }

    @Bean(name = "dwRedisTemplate")
    public RedisTemplate<String,Object> redisTemplate(@Qualifier(value = "jedisConnectionFactory") RedisConnectionFactory factory){
        logger.info("【初始化RedisTemplate】");
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        serializer.setObjectMapper(mapper);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

}
