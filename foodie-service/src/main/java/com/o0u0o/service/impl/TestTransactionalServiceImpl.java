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
 * 事物传播 -
 *  1、REQUIRED【使用最多】 ：使用当前的事物，如果当前没有事物，则自己新建一个事物
 *                自方法是必须运行在一个事物中的；如果当前存在事物，则加入这个
 *                事物，成为一个整体。（适合于 增、删、改）
 *                例子: 领导没饭吃，我有钱，我会自己买了自己吃；
 *                     领导有的吃，会分给我们吃
 *
 *  2、SUPPORTS【使用最多】： 如果当前有事物，则使用事物，如果当前没有事务，则不使用事务。（使用用于查询，不需要进行回滚操作）
 *               例子: 领导没饭吃 我也没饭吃
 *                     领导有饭吃，我也有饭吃
 *
 *  3、MANDATORY: 该转播属性强制必须存在一个事务，如果不存在，则抛出异常
 *                例子： 在公司，领导必须管饭，不管饭吃，我就不干了（抛出异常）
 *
 *  4、REQUIRES_NEW: 如果当前有事务，则挂起该事务，并且自己创建一个新的事务给自己使用，如果当前没有事务，则同REQUIRED
 *                   例子：领导有饭吃，我偏不要，我自己买了自己吃
 *
 *  5、NOT_SUPPORTED（不支持）: 如果当前有事务，则把事务挂起，自己不实用事务去运行数据库操作
 *                            举例：领导有饭吃，分一点给我，我太忙了，放一边，我不吃
 *
 *  6、NEVER（从不）: 如果当前有事务存在，则抛出异常
 *                  举例：领导有饭给你吃，我不想吃，我热爱工作，我抛出异常
 *
 *  7、NESTED（嵌套）: 如果当前有事务，则开启子事务（嵌套事务），嵌套事务是独立提交或者回滚
 *                    如果当前没有事务，则同 REQUIRED。 但是如果主事务提交，则会携带子事务一起提交。
 *                    如果主事务回滚，则子事务会一起回滚。相反，子事务异常，则父事务可以回滚，或者不回滚。
 *                    举例： 领单角色不多，老板怪罪，领导带着小弟一同受罪，小弟出了错误，领导可推卸责任，也可以帮小弟分担责任。
 *
 *
 *
 **/
@Service
public class TestTransactionalServiceImpl implements TestTransService {

    @Autowired
    private StuService stuService;

    @Transactional(propagation = Propagation.REQUIRED)
//    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void testPropagationTrans() {
        stuService.saveParent();
        try {
            //SAVE POINT 保存点
            stuService.saveChildren();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //delete

        //update

//        int a = 1 / 0;
    }
}
