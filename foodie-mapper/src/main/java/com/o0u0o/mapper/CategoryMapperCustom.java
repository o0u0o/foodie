package com.o0u0o.mapper;

import com.o0u0o.my.mapper.MyMapper;
import com.o0u0o.pojo.Category;
import com.o0u0o.pojo.vo.CategoryVO;

import java.util.List;

public interface CategoryMapperCustom {

    public List<CategoryVO> getSubCatList(Integer rootCatId);
}