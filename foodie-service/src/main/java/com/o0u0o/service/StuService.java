package com.o0u0o.service;

import com.o0u0o.pojo.Stu;

/**
 * @Author aiuiot
 * @Date 2020/5/24 5:29 下午
 * @Descripton:
 **/
public interface StuService {

    public Stu getStuInfo(int id);

    public void saveStu();

    public void upateStu(int id);

    public void deleteStu(int id);

    void saveParent();

    void saveChildren();
}
