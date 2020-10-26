package com.o0u0o.service;


/**
 * 用户业务服务接口
 * @author o0u0o
 * @date 2020/10/25 12:25 下午
 */
public interface UserService {

    /**
     * 判断用户名是否存在
     */
    public boolean queryUsernameIsExist(String username);
}
