package com.imooc.controller;

import com.imooc.pojo.Stu;
import com.imooc.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class Hello {
    @Autowired
    private StuService stuService;

    @GetMapping("/Hello")
    public Object Hello() {
        return "Hello~";
    }

    @GetMapping("/findStuInfo")
    public Stu findStuInfo(int id) {
        return stuService.findStuInfo(id);
    }

    @PostMapping("/saveStuInfo")
    public String saveStuInfo(String name, int age) {
        return stuService.saveStuInfo(name, age);
    }

    @PostMapping("/updateStu")
    public String updateStu(int id) {
        return stuService.updateStu(id);
    }

    @PostMapping("/deleteStu")
    public void deleteStu(int id) {
        stuService.deleteStu(id);

    }
}
