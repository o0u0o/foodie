package com.o0u0o.controller.usercenter;

import com.o0u0o.pojo.Orders;
import com.o0u0o.pojo.Users;
import com.o0u0o.service.usercenter.MyOrdersService;
import com.o0u0o.service.usercenter.UserCenterService;
import com.o0u0o.utils.IJsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户中心接口
 * @author mac
 * @date 2020/12/3 2:59 下午
 */
@Api(value = "center - 用户中心", tags = {"用户中心展示的相关接口"})
@RestController
@RequestMapping("/center")
public class CenterController {

    @Autowired
    private UserCenterService userCenterService;

    @Autowired
    private MyOrdersService myOrdersService;

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息", httpMethod = "GET")
    @GetMapping("/userInfo")
    public IJsonResult userInfo(@ApiParam(name = "userId", value = "用户ID", required = true)
                                @RequestParam String userId){
        Users user = userCenterService.queryUserInfo(userId);
        return IJsonResult.ok(user);
    }
}
