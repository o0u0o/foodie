package com.o0u0o.controller;

import com.o0u0o.service.OrderService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author mac
 * @date 2020/11/30 9:09 上午
 */
@Api(value = "订单相关", tags = {"订单相关的api接口"})
public class OrdersController extends BaseController {

    final static Logger logger = LoggerFactory.getLogger(OrdersController.class);

    @Autowired
    private OrderService orderService;
}
