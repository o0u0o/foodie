package com.o0u0o.service.impl;

import com.o0u0o.mapper.CategoryMapper;
import com.o0u0o.pojo.Category;
import com.o0u0o.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 分类服务实现类
 * @author mac
 * @date 2020/11/24 1:08 下午
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> queryAllRootLevelCat() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", 1);

        List<Category> result = categoryMapper.selectByExample(example);
        return result;
    }
}
