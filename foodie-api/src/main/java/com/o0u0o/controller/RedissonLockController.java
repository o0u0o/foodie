package com.o0u0o.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 *  RedissonLock 分布式锁测试
 * @author mac
 * @date 2020/12/2 4:30 下午
 */
@RestController
public class RedissonLockController {

    final static Logger logger = LoggerFactory.getLogger(RedissonLockController.class);

    @Autowired
    private RedissonClient redissonClient;

    @RequestMapping("/redissonLock")
    public String redissonLock(){

        RLock rLock = redissonClient.getLock("order_test");
        logger.info("我进入了方法");

        try {
            rLock.lock(30, TimeUnit.SECONDS);
            logger.info("我获得了锁");
            Thread.sleep(10000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            logger.info("方法执行完成");
            rLock.unlock();
        }

        return "ok";
    }


}
