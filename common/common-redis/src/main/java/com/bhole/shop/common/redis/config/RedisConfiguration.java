package com.bhole.shop.common.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @program: bhole-shop-common-redis
 * @description:
 * @author: joke
 * @date: 2023/6/7 16:12
 * @version: 1.0
 */
@Configuration
public class RedisConfiguration {


    @Bean
    public GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer(){
        ObjectMapper om = new ObjectMapper();
        // 解决查询缓存转换异常的问题
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping( LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        // 支持 jdk 1.8 日期
//        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        om.registerModule(new Jdk8Module())
//                .registerModule(new JavaTimeModule())
//                .registerModule(new ParameterNamesModule());
        return new GenericJackson2JsonRedisSerializer(om);
    }

    @Bean
    public RedisTemplate<String, Long> stringLongRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Long> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericToStringSerializer(Long.class));

        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(genericJackson2JsonRedisSerializer());
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(genericJackson2JsonRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisUtils redisUtils(RedisTemplate<String, Object> template) {
        return new RedisUtils(template);
    }
}
