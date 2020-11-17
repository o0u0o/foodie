package com.o0u0o.controller;

import com.o0u0o.pojo.Users;
import com.o0u0o.pojo.bo.UserBO;
import com.o0u0o.service.UserService;
import com.o0u0o.utils.CookieUtils;
import com.o0u0o.utils.IJsonResult;
import com.o0u0o.utils.JsonUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    /**
     * 判断用户名是否存在
     * @param username 用户名
     * @return
     */
    @GetMapping("/usernameIsExist")
    public IJsonResult usernameIsExist(@RequestParam String username){

        //1.判断用户名不能为空
        if (StringUtils.isBlank(username)){
            return IJsonResult.errorMsg("用户名不能为空");
        }

        //2.查找注册的用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist){
            return IJsonResult.errorMsg("用户名已经存在");
        }

        //3.请求成功，用户名没有重复
        return IJsonResult.ok();
    }

    @ApiOperation(value = "用户登陆", notes = "用户登陆", httpMethod = "POST")
    @PostMapping("/login")
    public IJsonResult login(){
        return null;
    }

    /**
     * 用户注册
     * @param userBO
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public IJsonResult regist(@RequestBody UserBO userBO,
                              HttpServletRequest request,
                              HttpServletResponse response){
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();

        //0.判断用户名和密码不为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)){
            return IJsonResult.errorMsg("用户名或密码不能为空");
        }

        //1.查询用户是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist){
            return IJsonResult.errorMsg("用户名已经存在");
        }

        //2.密码长度不能少于6位
        if (password.length() <  6){
            return IJsonResult.errorMsg("用户密码不能少于6位");
        }

        //3.判断两次密码是否一致
        if (!password.equals(confirmPassword)){
            return IJsonResult.errorMsg("两次密码不一致");
        }

        //4.实现注册
        Users userResult = userService.createUser(userBO);

        userResult = setNullProperty(userResult);

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);
        //TODO 生成用户token 存入redis会话

        //TODO 同步购物车数据
        return IJsonResult.ok(userResult);
    }


    //=========== PRIVATE ===========

    /**
     * 敏感信息置空
     * @param userResult
     * @return
     */
    private Users setNullProperty(Users userResult){
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }

}

