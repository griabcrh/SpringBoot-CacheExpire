package com.cachedemo.controller;

import com.cachedemo.entity.Student;
import com.cachedemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yiyang
 * @date 2022/9/14 14:21
 */
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping(value = "/save")
    public Student save(@RequestBody Student student) {
        return studentService.save(student);
    }

    @PostMapping(value = "/update")
    public Student update(@RequestBody Student student) {
        return studentService.update(student);
    }

    @PostMapping(value = "/queryByName")
    public Student queryByName(@RequestParam String name) {
        return studentService.queryByName(name);
    }

    @PostMapping(value = "/delete")
    public void delete(@RequestParam String name) {
        studentService.delete(name);
    }

}
