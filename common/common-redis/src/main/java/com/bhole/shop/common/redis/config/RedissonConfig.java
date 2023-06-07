package com.bhole.shop.common.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: bhole-shop-common-redis
 * @description:
 * @author: joke
 * @date: 2023/6/7 16:12
 * @version: 1.0
 */
@Configuration
public class RedissonConfig {

    @Value(value = "${spring.redis.port}")
    private String port;

    @Value(value = "${spring.redis.host}")
    private String host;
    @Value(value = "${spring.redis.password}")
    private String password;

    @Bean
    public RedissonClient getRedisson() {
        Config config = new Config();
        String address = host + ":" + port;
        config.useSingleServer().setAddress("redis://" + address).setPassword(password);
        return Redisson.create(config);
    }

}