package com.o0u0o.service.usercenter;

import com.o0u0o.pojo.Users;

public interface UserCenterService {

    /**
     * 根据用户ID查询用户信息
     * @param userId
     * @return
     */
    public Users queryUserInfo(String userId);
}
