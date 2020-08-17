package com.o0u0o.service.impl;

import com.o0u0o.service.StuService;
import com.o0u0o.service.TestTransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author aiuiot
 * @Date 2020/5/24 9:12 下午
 * @Descripton: 测试事物的服务实现
 * 事物传播
 *  1、REQUIRED ：使用当前的事物，如果当前没有事物，则自己新建一个事物
 *                自方法是必须运行在一个事物中的；如果当前存在事物，则加入这个
 *                事物，成为一个整体。（增、删、改）
 *                例子: 领导没饭吃，我有钱，我会自己买了自己吃；
 *                      领导有的吃，会分给我们吃
 *  2、SUPPORTS： 如果当前有事物，则使用事物，如果当前没有事物，则不使用事物。（查询）
 *               例子: 领导没饭吃 我也没饭吃
 *                    领导有饭吃，我也有饭吃
 *  3、
 *  4、
 *  5、
 *  6、
 *  7、
 *
 **/
@Service
public class TestTransactionalServiceImpl implements TestTransService {

    @Autowired
    private StuService stuService;

    /**
     * 开启事物
     */
    //@Transactional(propagation = Propagation.REQUIRED)
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void testPropagationTrans() {
        stuService.saveParent();
        stuService.saveChildren();
    }
}
