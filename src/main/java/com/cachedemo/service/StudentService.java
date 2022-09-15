package com.cachedemo.service;

import com.cachedemo.entity.Student;

/**
 * @author yiyang
 * @date 2022/9/14 14:11
 */

public interface StudentService {

    Student update(Student student);

    Student save(Student student);

    void delete(String name);

    Student queryByName(String name);

}
