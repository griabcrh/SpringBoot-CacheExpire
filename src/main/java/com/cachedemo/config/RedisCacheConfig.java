package com.cachedemo.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yiyang
 * @date 2022/9/15 14:11
 */
@Configuration
public class RedisCacheConfig {

    @Bean
    public CacheResolver redisExpireCacheResolver(CacheManager cacheManager) {
        return new RedisExpireCacheResolver(cacheManager);
    }
}
