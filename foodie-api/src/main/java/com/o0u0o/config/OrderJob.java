package com.o0u0o.config;

import com.o0u0o.service.shop.OrderService;
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


    /**
     * 自动关闭订单
     * 以1小时为区间 "0 0 0/1 * * ?"
     * 以3秒为区间 "0/3 * * * * ?"
     * 使用定时任务关闭超期未支付订单的弊端：
     * 1、由于需要对时间判断，会有时间差
     *      10：39下单 11：00 检查不足一小时， 12：00 检查 超过1小时
     * 2、不支持集群，如果有10个节点部署集群，每个集群会同时执行定时任务，
     *      解决方案：只使用一台计算机节点，单独来运行所有的定时任务
     * 3、最大的弊端：会对数据库进行全表搜索，及其影响数据库的性能
     *      如查询:select * from order where orderStatus = 10  影响数据库性能
     *
     * 总结：定时任务，仅仅只适用于小型轻量级项目，传统项目。互联网电商平台一般适用消息队列MQ
     *  常见消息队列：RabbitMQ、RocketMQ、Kafka、ZeroMQ...
     *
     *  延时任务（队列）
     *  10：12分下单的，未付款（10）状态， 会在11：12分检查，如果当前状态还是10，则直接关闭订单即可
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void autoCloseOrder(){
        orderService.closeOrder();
        System.out.println("执行定时任务，当前时间为:" + DateUtil.getCurrentDateString(DateUtil.DATETIME_PATTERN));

    }
}
