package com.imooc.controller;

import com.imooc.pojo.Stu;
import com.imooc.service.StuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "redis", tags = {"redis测试"})
@RestController("/redis")
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "contoller方法", notes = "contoller方法上面", httpMethod = "POST")
    @PostMapping("/set")
    public Object Hello(String key,String value) {
        redisTemplate.opsForValue().set(key,value);
        return "OK";
    }

    @GetMapping("/get")
    public String findStuInfo(String key) {
        return redisTemplate.opsForValue().get(key).toString();
    }

    @PostMapping("/del")
    public Object saveStuInfo(String key) {
        redisTemplate.delete(key);
        return "OK";
    }

}
