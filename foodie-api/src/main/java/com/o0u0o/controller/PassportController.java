package com.o0u0o.controller;

import com.o0u0o.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通行证接口
 * @author o0u0o
 * @date 2020/10/25 1:38 下午
 */
@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @GetMapping("/usernameIsExist")
    public HttpStatus usernameIsExist(@RequestParam String username){

        //1.判断用户名不能为空
        if (StringUtils.isBlank(username)){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        //2.查找注册的用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        //3.请求成功，用户名没有重复
        return HttpStatus.OK;
    }

}

