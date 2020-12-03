package com.o0u0o.config;

import com.o0u0o.service.OrderService;
import com.o0u0o.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 订单定时任务
 * 使用时需要在启动类添加 @EnableScheduling 开启定时任务
 * @author mac
 * @date 2020/12/3 2:12 下午
 */
@Component
public class OrderJob {

    @Autowired
    private OrderService orderService;

    //自动关闭订单
    @Scheduled(cron = "0/3 * * * * ?")
    public void autoCloseOrder(){
        System.out.println("执行定时任务，当前时间为:" + DateUtil.getCurrentDateString(DateUtil.DATETIME_PATTERN));
        orderService.closeOrder();
    }
}
