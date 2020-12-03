package com.o0u0o.controller.usercenter;

import com.o0u0o.pojo.Users;
import com.o0u0o.pojo.bo.center.CenterUserBO;
import com.o0u0o.service.usercenter.UserCenterService;
import com.o0u0o.utils.CookieUtils;
import com.o0u0o.utils.IJsonResult;
import com.o0u0o.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mac
 * @date 2020/12/3 3:25 下午
 */
@Api(value = "用户信息接口", tags = {"用户信息相关接口"})
@RestController
@RequestMapping("userInfo")
public class CenterUserInfoController {

    @Autowired
    private UserCenterService userCenterService;

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息", httpMethod = "GET")
    @PostMapping("/update")
    public IJsonResult update(@ApiParam(name = "userId", value = "用户ID", required = true)
                              @RequestParam String userId,
                              @RequestBody CenterUserBO centerUserBO,
                              HttpServletRequest request,
                              HttpServletResponse response){
        Users userResult = userCenterService.updateUserInfo(userId, centerUserBO);
        userResult = setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);

        // TODO 后续要改，增加令牌Token, 会整合进redis 实现分布式会话管理

        return IJsonResult.ok();

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
