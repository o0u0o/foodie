package com.o0u0o.controller;

import com.o0u0o.pojo.UserAddress;
import com.o0u0o.service.AddressService;
import com.o0u0o.utils.IJsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mac
 * @date 2020/11/26 10:58 下午
 */
@Api(value = "地址相关", tags = {"地址相关的api接口"})
@RequestMapping("address")
@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "根据用户id查询收货地址列表", notes = "根据用户id查询收货地址列表", httpMethod = "POST")
    @PostMapping("/list")
    public IJsonResult list(@RequestParam String userId){
        if (StringUtils.isBlank(userId)){
            return IJsonResult.errorMsg("");
        }
        List<UserAddress> userAddressList = addressService.queryAll(userId);
        return IJsonResult.ok(userAddressList);
    }

}
