package com.o0u0o.controller.shop;

import java.io.File;

/**
 * 通用Controller
 * @author mac
 * @date 2020/11/26 9:30 上午
 */
public class BaseController {

    public static final String FOODIE_SHOPCART = "shopcart";

    public static final Integer COMMENT_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;

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

}
