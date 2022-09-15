package com.cachedemo.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yiyang
 * @date 2022/9/14 14:12
 */
@Builder
@Data
public class Student implements Serializable {

    private String name;

    private Integer age;
}
