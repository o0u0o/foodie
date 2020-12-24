package com.o0u0o.service.usercenter.impl;

import com.github.pagehelper.PageInfo;
import com.o0u0o.utils.PagedGridResult;

import java.util.List;

/**
 * 基础实现类
 * @author mac
 * @date 2020/12/24 8:29 下午
 */
public class BaseService {

    public PagedGridResult setterPagedGrid(List<?> list, Integer pageNum){
        PageInfo<?> pageInfo = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(pageNum);
        grid.setRows(list);
        grid.setTotal(pageInfo.getPages());
        grid.setRecords(pageInfo.getTotal());
        return grid;
    }
}
