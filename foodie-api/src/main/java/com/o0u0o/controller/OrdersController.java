package com.o0u0o.controller;

import com.o0u0o.enums.PayMethod;
import com.o0u0o.pojo.bo.SubmitOrderBO;
import com.o0u0o.pojo.vo.OrderVO;
import com.o0u0o.service.OrderService;
import com.o0u0o.utils.CookieUtils;
import com.o0u0o.utils.IJsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mac
 * @date 2020/11/30 9:09 上午
 */
@Api(value = "订单相关", tags = {"订单相关的api接口"})
@RequestMapping("orders")
@RestController
public class OrdersController extends BaseController {

    final static Logger logger = LoggerFactory.getLogger(OrdersController.class);

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public IJsonResult create(@RequestBody SubmitOrderBO submitOrderBO,
                              HttpServletRequest request,
                              HttpServletResponse response){

        if (!submitOrderBO.getPayMethod().equals(PayMethod.WEIXIN.type)
                && !submitOrderBO.getPayMethod().equals(PayMethod.ALIPAY.type)){
            return IJsonResult.errorMsg("支付方式不支持!");
        }

        logger.info("提交的订单信息:{}", submitOrderBO);

        //1.创建订单
        OrderVO orderVO = orderService.createOrder(submitOrderBO);


        //2.创建订单后，移除购物车中已结算（已提交）的商品
        /**
         * 1001
         * 2002 -> 用户购买
         * 3003 -> 用户购买
         */
        //TODO 整合redis之后，完善购物车中已结算商品清除，并且同步到前端cookie
        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, "", true);

        //TODO 3.向支付中心发送当前订单，用于保存支付中心的订单数据


        return IJsonResult.ok(orderVO);
    }
}
