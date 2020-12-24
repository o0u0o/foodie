package com.o0u0o.service.usercenter;

import com.o0u0o.pojo.OrderItems;
import com.o0u0o.pojo.bo.center.OrderItemsCommentBO;
import com.o0u0o.utils.PagedGridResult;

import java.util.List;

public interface MyCommentsService {

    /**
     * 根据订单ID查询关联的商品
     * @param orderId 订单ID
     * @return
     */
    public List<OrderItems> queryPendingComment(String orderId);

    /**
     * 保存用户的评论
     * @param orderId 订单ID
     * @param userId  用户ID
     * @param commentList 评论列表
     */
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList);

    /**
     * 我的评论查询 分页
     * @param userId    用户ID
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PagedGridResult queryMyComments(String userId, Integer pageNum, Integer pageSize);
}
