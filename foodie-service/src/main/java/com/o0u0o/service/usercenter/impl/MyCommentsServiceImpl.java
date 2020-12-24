package com.o0u0o.service.usercenter.impl;

import com.github.pagehelper.PageHelper;
import com.o0u0o.enums.YesOrNo;
import com.o0u0o.mapper.*;
import com.o0u0o.pojo.OrderItems;
import com.o0u0o.pojo.OrderStatus;
import com.o0u0o.pojo.Orders;
import com.o0u0o.pojo.bo.center.OrderItemsCommentBO;
import com.o0u0o.service.usercenter.MyCommentsService;
import com.o0u0o.utils.PagedGridResult;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.index.PathBasedRedisIndexDefinition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的评论服务实现
 * @author mac
 * @date 2020/12/24 4:59 下午
 */
@Service
public class MyCommentsServiceImpl implements MyCommentsService {

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private ItemsCommentsMapperCustom itemsCommentsMapperCustom;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private Sid sid;

    /**
     * 根据订单ID查询关联的商品
     * @param orderId 订单ID
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<OrderItems> queryPendingComment(String orderId) {
        OrderItems query = new OrderItems();
        query.setOrderId(orderId);

        return orderItemsMapper.select(query);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComments(String orderId,
                             String userId,
                             List<OrderItemsCommentBO> commentList) {
        //1.保存评价 items_comments表
        for (OrderItemsCommentBO oic : commentList) {
            oic.setCommentId(sid.nextShort());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentList", commentList);
        itemsCommentsMapperCustom.saveComments(map);

        //2.修改订单表(order)为已评价
        Orders order = new Orders();
        order.setId(orderId);
        order.setIsComment(YesOrNo.YES.type);
        ordersMapper.updateByPrimaryKeySelective(order);

        //3. 修改订单状态表(order_status) 设置留言时间
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyComments(String userId, Integer pageNum, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        PageHelper.startPage(pageNum, pageSize);

        return null;
    }
}
