package com.cachedemo.config;

import com.cachedemo.annotation.CacheExpire;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author yiyang
 * @date 2022/9/15 13:45
 */
public class RedisExpireCacheResolver extends SimpleCacheResolver {

    public RedisExpireCacheResolver(CacheManager cacheManager) {
        super(cacheManager);
    }

    @Override
    public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
        Collection<String> cacheNames = getCacheNames(context);
        if(cacheNames == null) {
            return Collections.emptyList();
        }
        Collection<Cache> result = new ArrayList<>(cacheNames.size());
        for (String cacheName : cacheNames) {
            Cache cache = getCacheManager().getCache(cacheName);
            if(cache == null) {
                throw new IllegalArgumentException("Cannot find cache named " + cacheName + " for " + context.getOperation()
                );
            }
            // 获取到Cache对象后，开始解析@CacheExpire
            parseCacheExpire(cache, context);
            result.add(cache);
        }
        return result;
    }

    private void parseCacheExpire(Cache cache, CacheOperationInvocationContext context) {
        Method method = context.getMethod();
        // 方法上是否标注了CacheExpire
        if(AnnotatedElementUtils.isAnnotated(method, CacheExpire.class)) {
            // 获取对象
            CacheExpire cacheExpire = AnnotationUtils.getAnnotation(method, CacheExpire.class);
            // 将cache强制转换成RedisCache,准备替换掉配置
            RedisCache redisCache = (RedisCache) cache;
            Duration duration = Duration.ofSeconds(cacheExpire.unit().toSeconds(cacheExpire.ttl()));
            // 替换RedisCacheConfiguration对象
            setRedisCacheConfiguration(redisCache, duration);
        }
    }

    private void setRedisCacheConfiguration(RedisCache redisCache, Duration duration) {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .entryTtl(duration);
        // 实践发现可以其换掉private final的field值
        // 反射设置新的值
        Field configField = ReflectionUtils.findField(RedisCache.class, "cacheConfig", RedisCacheConfiguration.class);
        configField.setAccessible(true);
        ReflectionUtils.setField(configField, redisCache, configuration);
    }
}
