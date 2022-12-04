package com.yunwuye.sample.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 *
 * @author Roy
 *
 * @date 2022年12月4日-下午3:43:19
 */
@Configuration
public class RedsiConfig{

    @Bean
    public RedisTemplate<String,Object> redisTemplate(LettuceConnectionFactory factory){
        RedisTemplate<String,Object> template = new RedisTemplate<>();

        template.setConnectionFactory(factory);

        Jackson2JsonRedisSerializer jacksonSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jacksonSerializer.setObjectMapper(objectMapper);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //使用注解@Bean返回RedisTemplate时，同时配置hashKey与HashValue的序列化方式
        //key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        //value序列化方式采用Jackson
        template.setValueSerializer(jacksonSerializer);

        //hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        //hash的vlaue序列化采用Jackson
        template.setHashValueSerializer(jacksonSerializer);

        template.afterPropertiesSet();

        return template;
    }

}
