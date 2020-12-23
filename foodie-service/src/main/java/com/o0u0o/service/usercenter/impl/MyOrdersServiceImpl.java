package com.o0u0o.service.usercenter.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.o0u0o.mapper.OrdersMapperCustom;
import com.o0u0o.pojo.vo.MyOrdersVO;
import com.o0u0o.service.usercenter.MyOrdersService;
import com.o0u0o.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的订单业务实现类
 * @author mac
 * @date 2020/12/23 9:24 上午
 */
@Service
public class MyOrdersServiceImpl implements MyOrdersService {

    @Autowired
    private OrdersMapperCustom ordersMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyOrders(String userId,
                                         Integer orderStatus,
                                         Integer pageNum,
                                         Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        if (orderStatus != null){
            map.put("orderStatus", orderStatus);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<MyOrdersVO> myOrdersVOList = ordersMapperCustom.queryMyOrders(map);

        return setterPagedGrid(myOrdersVOList, pageNum);
    }

    //========== PRIVATE METHOD ==========

    private PagedGridResult setterPagedGrid(List<?> list, Integer pageNum){
        PageInfo<?> pageInfo = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(pageNum);
        grid.setRows(list);
        grid.setTotal(pageInfo.getPages());
        grid.setRecords(pageInfo.getTotal());
        return grid;
    }

}
