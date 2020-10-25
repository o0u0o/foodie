package com.o0u0o.service.impl;

import com.o0u0o.mapper.StuMapper;
import com.o0u0o.pojo.Stu;
import com.o0u0o.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author aiuiot
 * @Date 2020/5/24 5:36 下午
 * @Descripton:
 * 事物的传播:
 * REQUIRED 默认的 当前方法不存在事物，创建一个，如果有，加入现有的事物
 *
 **/
@Service
public class StuServiceImpl implements StuService {

    @Autowired
    private StuMapper stuMapper;

    @Override
    public void saveParent(){
        Stu stu = new Stu();
        stu.setName("parent");
        stu.setAge(19);
        stu.setGender("男");
        stuMapper.insert(stu);
    }

    @Transactional(propagation = Propagation.NESTED)
//    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void saveChildren(){
        saveChild1();
//        int a = 1 / 0;
        saveChild2();
    }

    public void saveChild1(){
        Stu stu1 = new Stu();
        stu1.setName("child-1");
        stu1.setAge(11);
        stu1.setGender("男");
        stuMapper.insert(stu1);
    }

    public void saveChild2(){
        Stu stu2 = new Stu();
        stu2.setName("child-2");
        stu2.setAge(12);
        stu2.setGender("女");
        stuMapper.insert(stu2);
    }

    /**
     * 查询
     * 添加了事物的支持 事务的传播 默认-REQUIRED
     * @param id
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Stu getStuInfo(int id) {
        return stuMapper.selectByPrimaryKey(id);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveStu() {
        Stu stu = new Stu();
        stu.setName("李四");
        stu.setAge(19);
        stu.setGender("女");
        stuMapper.insert(stu);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void upateStu(int id) {
        Stu stu = new Stu();
        stu.setId(id);
        stu.setName("王武");
        stu.setAge(19);
        stu.setGender("女");
        stuMapper.updateByPrimaryKey(stu);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteStu(int id) {
        stuMapper.deleteByPrimaryKey(id);
    }
}
