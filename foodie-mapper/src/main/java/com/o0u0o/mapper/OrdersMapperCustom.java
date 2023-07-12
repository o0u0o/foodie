package com.o0u0o.mapper;

import com.o0u0o.pojo.OrderStatus;
import com.o0u0o.pojo.vo.MyOrdersVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrdersMapperCustom {

    public List<MyOrdersVO> queryMyOrders(@Param("paramsMap")Map<String, Object> map);

    //查询子订单
    public int getMyOrderStatusCounts(@Param("paramsMap")Map<String, Object> map);

    public List<OrderStatus> getMyOrderTrend(@Param("paramsMap")Map<String, Object> map);

}
