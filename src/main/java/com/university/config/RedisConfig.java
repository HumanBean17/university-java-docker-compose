package com.university.config;

import com.university.entity.StudentRedis;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, StudentRedis> redisTemplate() {
        RedisTemplate<String, StudentRedis> template = new RedisTemplate<>();
        template.setStringSerializer(new StringRedisSerializer());
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

}
