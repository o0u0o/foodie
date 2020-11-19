package com.o0u0o.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author aiuiot
 * @Date 2020/5/12 10:56 下午
 * @Descripton: 测试接口
 **/
//swagger2 忽略当前类所有接口
@ApiIgnore
@RestController
public class HelloController {

    @GetMapping("/hello")
    public Object hello(){
        return "hello world";
    }

    /**
     * 设置session演示设置
     * @param request
     * @return
     */
    @GetMapping("/setSession")
    public Object setSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("userInfo",  "new user");
        session.setMaxInactiveInterval(3600);
        session.getAttribute("userInfo");
        //手动移除
//        session.removeAttribute("userInfo");
        return "ok";
    }
}
