package com.o0u0o.controller.usercenter;

import com.o0u0o.pojo.Orders;
import com.o0u0o.pojo.Users;
import com.o0u0o.service.usercenter.MyOrdersService;
import com.o0u0o.service.usercenter.UserCenterService;
import com.o0u0o.utils.IJsonResult;
import com.o0u0o.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 用户中心接口
 * @author mac
 * @date 2020/12/3 2:59 下午
 */
@Api(value = "用户中心 - 我的订单", tags = {"用户中心我的订单相关接口"})
@RestController
@RequestMapping("/myorders")
public class MyOrdersController {

    @Autowired
    private MyOrdersService myOrdersService;

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("/query")
    public IJsonResult comments(
            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderStatus", value = "订单状态", required = false)
            @RequestParam Integer orderStatus,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize){

        if (StringUtils.isBlank(userId)){
            return IJsonResult.errorMsg(null);
        }

        PagedGridResult gridResult = myOrdersService.queryMyOrders(userId, orderStatus, page, pageSize);
        return IJsonResult.ok(gridResult);
    }

    /**
     * 商家发货没有后端，所以这个接口仅仅用于模拟
     * @param orderId 订单号
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "商家发货", notes = "商家发货", httpMethod = "GET")
    @GetMapping("/deliver")
    public IJsonResult deliver(@ApiParam(name = "orderId", value = "订单ID", required = true)
                               @RequestParam String orderId) throws Exception{
        if (StringUtils.isBlank(orderId)){
            return IJsonResult.errorMsg("订单ID不能为空");
        }
        myOrdersService.updateDeliverOrderStatus(orderId);
        return IJsonResult.ok();
    }

    @ApiOperation(value = "用户确认收货", notes = "用户确认收货", httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public IJsonResult confirmReceive(@ApiParam(name = "orderId", value = "订单ID", required = true)
                                          @RequestParam String orderId,
                                     @ApiParam(name = "userId", value = "用户ID", required = true)
                                          @RequestParam String userId) throws Exception{
        IJsonResult checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()){
            return checkResult;
        }
        return IJsonResult.ok();
    }


    //========== PRIVATE METHOD ==========

    /**
     * 用于验证用户订单是否有关联关系，避免非法用户调用
     * @param orderId
     * @return
     */
    private IJsonResult checkUserOrder(String userId ,String orderId){
        Orders order = myOrdersService.queryMyOrder(userId, orderId);
        if (order == null){
            return IJsonResult.errorMsg("订单不存在！");
        }
        return IJsonResult.ok();
    }

}
