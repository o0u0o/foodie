package com.o0u0o.service;

import com.o0u0o.pojo.Category;

import java.util.List;

public interface CategoryService {

    /**
     * 查询所有根级分类
     * @return
     */
    public List<Category> queryAllRootLevelCat();
}
