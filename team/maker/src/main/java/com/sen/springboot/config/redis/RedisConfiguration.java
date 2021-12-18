package com.sen.springboot.config.redis;


import com.sen.springboot.common.RedisUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfiguration {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        //RedisTemplate 相当于JDBCTemplate
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        //序列化 KEY,String储存
        template.setKeySerializer(RedisSerializer.string());
        //序列化 VAL,JSON储存
        template.setValueSerializer(RedisSerializer.json());
        return template;
    }

    @Bean(name = "redisUtil")
    public RedisUtil redisUtil(RedisTemplate<String, Object> redisTemplate) {
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.setRedisTemplate(redisTemplate);
        return redisUtil;
    }
}
