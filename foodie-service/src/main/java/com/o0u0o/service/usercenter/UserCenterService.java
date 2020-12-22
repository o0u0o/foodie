package com.o0u0o.service.usercenter;

import com.o0u0o.pojo.Users;
import com.o0u0o.pojo.bo.center.CenterUserBO;

public interface UserCenterService {

    /**
     * 根据用户ID查询用户信息
     * @param userId
     * @return
     */
    public Users queryUserInfo(String userId);

    /**
     * 修改用户信息
     * @param userId
     * @param centerUserBO
     */
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO);

    /**
     * 用户头像更新
     * @param userId
     * @param faceUrl
     * @return
     */
    public Users updateUserFace(String userId, String faceUrl);
}
