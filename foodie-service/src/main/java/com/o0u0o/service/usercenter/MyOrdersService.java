package com.o0u0o.service.usercenter;

import com.o0u0o.pojo.Orders;
import com.o0u0o.pojo.Users;
import com.o0u0o.pojo.bo.center.CenterUserBO;
import com.o0u0o.utils.PagedGridResult;

public interface MyOrdersService {

    public PagedGridResult queryMyOrders(String userId,
                                         Integer orderStatus,
                                         Integer pageNum,
                                         Integer pageSize);

    /**
     * 订单状态 --> 商家发货
     * @param orderId 订单ID
     */
    public void updateDeliverOrderStatus(String orderId);

    /**
     * 查询我的订单
     * @param userId 用户ID
     * @param orderId 订单ID
     * @return
     */
    public Orders queryMyOrder(String userId, String orderId);

    /**
     * 确认收货
     * @param orderId 订单ID
     * @return
     */
    public boolean updateReceiveOrderStatus(String orderId);

    /**
     * 删除订单 - 逻辑删除(更新状态)
     * @param userId 用户ID
     * @param orderId 订单ID
     * @return
     */
    public boolean deleteOrder(String userId, String orderId);
}
