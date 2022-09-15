package com.cachedemo.service.impl;

import com.cachedemo.annotation.CacheExpire;
import com.cachedemo.entity.Student;
import com.cachedemo.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author yiyang
 * @date 2022/9/14 14:16
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "student")
public class StudentServiceImpl implements StudentService {

    @Override
    @CachePut(key = "#student.name")
    public Student update(Student student) {
        log.info("进入更新方法体");
        return student;
    }

    @Override
    @CachePut(key = "#student.name")
    public Student save(Student student) {
        log.info("进入新增方法体");
        return student;
    }

    @Override
    @CacheEvict(key = "#name")
    public void delete(String name) {
        log.info("进入删除方法体");
    }

    @Override
    @Cacheable( key = "#name", cacheResolver = "redisExpireCacheResolver")
    @CacheExpire(ttl = 600)
    public Student queryByName(String name) {
        log.info("进入查询方法体");
        return Student.builder().age(18).name("zhangsan").build();
    }
}
