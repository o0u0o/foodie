package com.o0u0o.service.usercenter.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.o0u0o.enums.OrderStatusEnum;
import com.o0u0o.mapper.OrderStatusMapper;
import com.o0u0o.mapper.OrdersMapperCustom;
import com.o0u0o.pojo.OrderStatus;
import com.o0u0o.pojo.vo.MyOrdersVO;
import com.o0u0o.service.usercenter.MyOrdersService;
import com.o0u0o.utils.PagedGridResult;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
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

    @Autowired
    private OrderStatusMapper orderStatusMapper;

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

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateDeliverOrderStatus(String orderId) {
        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
        updateOrder.setDeliverTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);

        orderStatusMapper.updateByExampleSelective(updateOrder, example);
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
