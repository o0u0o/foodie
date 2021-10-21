package com.o0u0o.controller.shop;

import com.o0u0o.pojo.Orders;
import com.o0u0o.pojo.Users;
import com.o0u0o.pojo.vo.UsersVO;
import com.o0u0o.service.usercenter.MyOrdersService;
import com.o0u0o.utils.IJsonResult;
import com.o0u0o.utils.RedisOperator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.UUID;

/**
 * 通用Controller
 * @author mac
 * @date 2020/11/26 9:30 上午
 */
public class BaseController {

    @Autowired
    private MyOrdersService myOrdersService;

    @Autowired
    private RedisOperator redisOperator;

    public static final String FOODIE_SHOPCART = "shopcart";

    public static final Integer COMMENT_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;

    public static final String REDIS_USER_TOKEN = "redis_user_token";

    //支付中心的调用地址
    String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    //微信支付成功 -> 支付中心 -> 天天吃货平台 -> 回调通知的url
    String payReturnUrl = "http://isale.natapp1.cc/orders/notifyMerchantOrderPaid";
//    String payReturnUrl = "http://localhost:8088/orders/notifyMerchantOrderPaid";

    //用户上传头像的地址
    public static final String IMAGE_USER_FACE_LOCATION = File.separator + "Users" +
                                                          File.separator + "mac" +
                                                          File.separator +"images" +
                                                          File.separator + "foodie" +
                                                          File.separator +"faces";


    //========== PRIVATE METHOD ==========
    /**
     * 用于验证用户订单是否有关联关系，避免非法用户调用
     * @param orderId
     * @return
     */
    public IJsonResult checkUserOrder(String userId , String orderId){
        Orders order = myOrdersService.queryMyOrder(userId, orderId);
        if (order == null){
            return IJsonResult.errorMsg("订单不存在！");
        }
        return IJsonResult.ok(order);
    }

    /**
     * 实现用户的redis会话 生成用户token 存入redis会话
     * @param userResult
     * @return
     */
    public UsersVO conventUsersVO(Users userResult){
        //实现用户的redis会话 生成用户token 存入redis会话
        String uniqueToken = UUID.randomUUID().toString().trim();
        redisOperator.set(REDIS_USER_TOKEN + ":" + userResult.getId(), uniqueToken);

        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(userResult, usersVO);
        usersVO.setUserUniqueToken(uniqueToken);
        return usersVO;
    }

}
