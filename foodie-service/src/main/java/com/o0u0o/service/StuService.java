package com.o0u0o.service;

import com.o0u0o.pojo.Stu;

/**
 * @Author aiuiot
 * @Date 2020/5/24 5:29 下午
 * @Descripton:
 **/
public interface StuService {

    /**
     * 查询学生信息
     * @param id 主键ID
     * @return
     */
    public Stu getStuInfo(int id);

    /***
     * 保存学生信息
     */
    public void saveStu();

    /**
     * 更新学生信息
     * @param id 主键ID
     */
    public void upateStu(int id);

    /***
     * 删除学生信息
     * @param id 主键ID
     */
    public void deleteStu(int id);

    void saveParent();

    void saveChildren();
}
