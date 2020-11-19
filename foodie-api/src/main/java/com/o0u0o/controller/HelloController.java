package com.o0u0o.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Author aiuiot
 * @Date 2020/5/12 10:56 下午
 * @Descripton:
 **/
//swagger2 忽略当前类所有接口
@ApiIgnore
@RestController
public class HelloController {

    @GetMapping("/hello")
    public Object hello(){
        return "hello world";
    }
}
