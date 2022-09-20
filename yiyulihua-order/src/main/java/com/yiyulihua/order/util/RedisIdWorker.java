package com.yiyulihua.order.util;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author sunbo
 * @since 2022/09/01 14:08
 */

@Component
public class RedisIdWorker {
    /**
     * 2022/01/01 的秒数
     */
    private static final long BEGIN_TIMESTAMP = 1640995200L;
    private static final int COUNT_BIT = 32;

    private final StringRedisTemplate redisTemplate;

    public RedisIdWorker(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public long nextId(String keyPrefix) {
        // 1.生成时间戳
        long timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - BEGIN_TIMESTAMP;
        // 2.生成序列号
        // redis 自增是有上限的,最大是 2^64 ,我们设置的序列号的位数最大是 32 位,一直自增一个 key 的话
        // 容易超出范围,在 key 后面添加日期数,可以避免该问题同时也易于统计
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        // 自增长,如果没有该 key 会从零开始自增
        long count = redisTemplate.opsForValue().increment("icr:" + keyPrefix + ":" + date);
        // 3.拼接返回
        // 时间戳左移 32 位空出序列号长度,拼接序列号课采用按位 | 运算 , 0|0 = 0, 1|0 = 1
        return (timestamp << COUNT_BIT) | count;
    }
}
