package com.o0u0o.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一认证管理系统Controller
 * @Author o0u0o
 * @Date 2020/5/12 10:56 下午
 * @Descripton: 统一认证管理系统Controller
 **/
@Controller
public class CASController {

    @GetMapping("/hello")
    @ResponseBody
    public Object hello(){
        return "hello world";
    }
}
