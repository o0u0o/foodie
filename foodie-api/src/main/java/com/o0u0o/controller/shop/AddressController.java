package com.o0u0o.controller.shop;

import com.o0u0o.pojo.UserAddress;
import com.o0u0o.pojo.bo.AddressBO;
import com.o0u0o.service.shop.AddressService;
import com.o0u0o.utils.IJsonResult;
import com.o0u0o.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "用户新增地址", notes = "用户新增地址", httpMethod = "POST")
    @PostMapping("/add")
    public IJsonResult add(@RequestBody AddressBO addressBO){

        IJsonResult checkRes = checkAddress(addressBO);
        if (checkRes.getStatus() != 200){
            return checkRes;
        }

        addressService.addNewUserAddress(addressBO);
        return IJsonResult.ok();
    }

    @ApiOperation(value = "用户修改地址", notes = "用户修改地址", httpMethod = "POST")
    @PostMapping("/update")
    public IJsonResult update(@RequestBody AddressBO addressBO){
        if (StringUtils.isBlank(addressBO.getAddressId())){
            return IJsonResult.errorMsg("修改地址错误：addressId不能为空");
        }

        IJsonResult checkRes = checkAddress(addressBO);
        if (checkRes.getStatus() != 200){
            return checkRes;
        }

        addressService.updateUserAddress(addressBO);
        return IJsonResult.ok();
    }

    @ApiOperation(value = "用户删除地址", notes = "用户删除地址", httpMethod = "POST")
    @PostMapping("/delete")
    public IJsonResult delete(@RequestParam(required = true) String userId,
                              @RequestParam(required = true) String addressId){
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)){
            return IJsonResult.errorMsg("");
        }

        addressService.deleteUserAddress(userId, addressId);
        return IJsonResult.ok();
    }

    @ApiOperation(value = "用户设置默认地址", notes = "用户设置默认地址", httpMethod = "POST")
    @PostMapping("/setDefault")
    public IJsonResult setDefault(@RequestParam String userId,
                                  @RequestParam String addressId){
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)){
            return IJsonResult.errorMsg("");
        }

        addressService.updateUserAddressToBeDefault(userId, addressId);
        return IJsonResult.ok();
    }

    private IJsonResult checkAddress(AddressBO addressBO){
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)){
            return  IJsonResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12){
            return IJsonResult.errorMsg("收货人姓名不能太长");
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)){
            return IJsonResult.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length()  != 11){
            return IJsonResult.errorMsg("收货人手机号长度不正确");
        }
        boolean mobileIsOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!mobileIsOk){
            return IJsonResult.errorMsg("收货人手机号格式不正确");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)){
            return IJsonResult.errorMsg("收货地址信息不能为空");
        }

        return IJsonResult.ok();
    }

}
