package com.o0u0o.service.shop;


import com.o0u0o.pojo.Users;
import com.o0u0o.pojo.bo.UserBO;

/**
 * 用户业务服务接口
 * @author o0u0o
 * @date 2020/10/25 12:25 下午
 */
public interface UserService {

    /**
     * 判断用户名是否存在
     * @param username 用户名
     * @return
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * 创建用户
     * @param userBo 用户BO
     * @return
     */
    public Users createUser(UserBO userBo);

    /**
     * 检索用户名和密码是否匹配，用于登陆
     * @param username
     * @param password
     * @return
     */
    public Users queryUserForLogin(String username, String password);
}
