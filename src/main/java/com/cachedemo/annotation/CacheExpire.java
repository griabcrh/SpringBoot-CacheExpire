package com.cachedemo.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author yiyang
 * @date 2022/9/15 13:38
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheExpire {

    public long ttl() default 60L;

    public TimeUnit unit() default TimeUnit.SECONDS;
}
