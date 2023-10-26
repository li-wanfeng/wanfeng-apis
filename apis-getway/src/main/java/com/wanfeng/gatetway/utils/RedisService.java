package com.wanfeng.gatetway.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisService {
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 判断key是否存在，存在不做操作，不存在添加
     * @param redisKey
     * @param i
     * @param time
     * @param timeUnit
     */
    public void setnxCacheObject(String redisKey, int i, Long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().setIfAbsent(redisKey, i, time, timeUnit);
    }

    public long decrementValue(String redisKey) {
        return redisTemplate.opsForValue().decrement(redisKey);
    }
}
