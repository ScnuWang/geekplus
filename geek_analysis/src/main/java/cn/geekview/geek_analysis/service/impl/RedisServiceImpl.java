package cn.geekview.geek_analysis.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service("RedisServiceImpl")
public class RedisServiceImpl {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }


    public void set(String key, Object object) {
        redisTemplate.opsForValue().set(key,object);
    }


    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }


    public Object getObject(String key) {
        return redisTemplate.opsForValue().get(key);
    }


    public void delete(String key) {
        redisTemplate.delete(key);
    }


    public boolean expire(String key, long timeout) {
        return redisTemplate.expire(key,timeout, TimeUnit.SECONDS);
    }

}
