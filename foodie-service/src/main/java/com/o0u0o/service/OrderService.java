package com.o0u0o.service;

import com.o0u0o.pojo.OrderStatus;
import com.o0u0o.pojo.bo.SubmitOrderBO;
import com.o0u0o.pojo.vo.OrderVO;

public interface OrderService {

    /**
     * 用于创建订单相关信息
     * @param submitOrderBO
     * @return
     */
    public OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     * @param orderId 订单号
     * @param orderStatus 订单状态
     */
    public void updateOrderStatus(String orderId, Integer orderStatus);

    /**
     * 查询订单状态
     * @param orderId 订单号
     * @return
     */
    public OrderStatus queryOrderStatusInfo(String orderId);
}
