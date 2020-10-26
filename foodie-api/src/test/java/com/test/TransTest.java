package com.test;

import com.o0u0o.Application;
import com.o0u0o.service.StuService;
import com.o0u0o.service.TestTransService;
import com.o0u0o.service.impl.TestTransactionalServiceImpl;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author aiuiot
 * @Date 2020/5/24 9:15 下午
 * @Descripton: 事务演示测试
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TransTest {

    @Autowired
    private StuService stuService;

    @Autowired
    private TestTransService testTransService;

    /**
     * 测试事务
     */
    @Ignore
    @Test
    public void myTest(){
        testTransService.testPropagationTrans();
    }
}
