package com.o0u0o.controller.shop;

import com.o0u0o.pojo.Users;
import com.o0u0o.pojo.bo.UserBO;
import com.o0u0o.pojo.vo.UsersVO;
import com.o0u0o.service.shop.UserService;
import com.o0u0o.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 通行证接口
 * @author o0u0o
 * @date 2020/10/25 1:38 下午
 */
@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("passport")
public class PassportController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "用户是否存在", notes = "用户是否存在", httpMethod = "GET")
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

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public IJsonResult register(@RequestBody UserBO userBO,
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

        //userResult = setNullProperty(userResult);

        //实现用户的redis会话 生成用户token 存入redis会话
        UsersVO usersVO = conventUsersVO(userResult);


        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(usersVO), true);

        //TODO 同步购物车数据

        return IJsonResult.ok(userResult);
    }

    @ApiOperation(value = "用户登陆", notes = "用户登陆", httpMethod = "POST")
    @PostMapping("/login")
    public IJsonResult login(@RequestBody UserBO userBO,
                             HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        String username = userBO.getUsername();
        String password = userBO.getPassword();

        //0.判断用户名密码必须不能为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return IJsonResult.errorMsg("用户名或密码为空");
        }

        //1、实现登录
        Users userResult = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));

        if (userResult == null){
            return IJsonResult.errorMsg("用户名或密码不正确");
        }

        // 生成用户token，存入redis会话
        UsersVO usersVO = conventUsersVO(userResult);
        //userResult = setNullProperty(userResult);

        //isEncode 是否设置cookie加密
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(usersVO), true);

        // TODO 同步购物车数据
        return IJsonResult.ok(usersVO);
    }

    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public IJsonResult logout(@RequestParam String userId,
                              HttpServletRequest request,
                              HttpServletResponse response){
        //清除用户相关信息的cookie
        CookieUtils.deleteCookie(request, response, "user");

        //分布式会话中需要清除用户数据
        redisOperator.del(REDIS_USER_TOKEN + ":" + userId);

        //TODO 用户退出登录，需要清空购物车


        return IJsonResult.ok();
    }




    //=========== PRIVATE ===========

    /**
     * 实现用户的redis会话 生成用户token 存入redis会话
     * @param userResult
     * @return
     */
    private UsersVO conventUsersVO(Users userResult){
        //实现用户的redis会话 生成用户token 存入redis会话
        String uniqueToken = UUID.randomUUID().toString().trim();
        redisOperator.set(REDIS_USER_TOKEN + ":" + userResult.getId(), uniqueToken);

        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(userResult, usersVO);
        usersVO.setUserUniqueToken(uniqueToken);
        return usersVO;
    }

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

