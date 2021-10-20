package com.o0u0o.controller;

import com.o0u0o.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Redis测试接口
 * @author o0u0o
 * @date 2021/1/20 10:12 下午
 */
@ApiIgnore
@RestController
@RequestMapping("redis")
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisOperator redisOperator;

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

    /**
     * 大量的key的查询 非批量
     * 对redis的key进行大量查询 优化工作
     * @param keys
     * @return
     */
    @GetMapping("/getAlot")
    public Object getAlot(String... keys){

        //1、循环的方式去获取keys
        List<String> result = new ArrayList<>();
        for (String k: keys){
            result.add(redisOperator.get(k));
        }
        return result;
    }

    /**
     * 批量查询mget（提高吞吐量）
     * 对redis的key进行大量查询 优化工作
     * 2、也可通过管道的功能来查询 如下
     * @param keys
     * @return
     */
    @GetMapping("/mget")
    public Object mget(String... keys){
        List<String> keysList = Arrays.asList(keys);
        return redisOperator.get(keysList);
    }

    /**
     * 管道方式批量获取
     * @param keys
     * @return
     */
    @GetMapping("/batchGet")
    public Object batchGet(String... keys){
        List<String> keysList = Arrays.asList(keys);
        return redisOperator.batchGet(keysList);
    }

}
