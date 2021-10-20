package com.o0u0o.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Redis测试接口
 * @author o0u0o
 * @date 2021/1/20 10:12 下午
 */
@ApiIgnore
@RestController("redis")
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/set")
    public Object set(String key, String value){
        //设置字符串类型的
        redisTemplate.opsForValue().set(key, value);
        return "OK";
    }

    @GetMapping("/get")
    public Object get(String key){
        return  (String)redisTemplate.opsForValue().get(key);
    }

    @GetMapping("/delete")
    public Object delete(String key){
        Boolean delete = redisTemplate.delete(key);
        if (delete != false){
            return "delete fail!";
        }
        return "delete success!";
    }


}
