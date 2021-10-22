package com.o0u0o.controller;

import com.sun.deploy.net.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一认证管理系统Controller
 * @Author o0u0o
 * @Date 2020/5/12 10:56 下午
 * @Descripton: 统一认证管理系统Controller
 **/
@Controller
public class CASController {


    @GetMapping("/login")
    public String login(HttpServletRequest request,
                        HttpResponse response,
                        String returnUrl,
                        Model model){
        model.addAttribute("returnUrl", returnUrl);

        //todo 后续完善校验是否登录

        // 用户从未登录过，第一次进入则跳转到cas的统一登录页面
        return "login";
    }
}
