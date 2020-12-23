package com.o0u0o.service.usercenter;

import com.o0u0o.pojo.Users;
import com.o0u0o.pojo.bo.center.CenterUserBO;
import com.o0u0o.utils.PagedGridResult;

public interface MyOrdersService {

    public PagedGridResult queryMyOrders(String userId,
                                         Integer orderStatus,
                                         Integer pageNum,
                                         Integer pageSize);
}
