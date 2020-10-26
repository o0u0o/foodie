package com.o0u0o.service.impl;

import com.o0u0o.mapper.UsersMapper;
import com.o0u0o.pojo.Users;
import com.o0u0o.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

/**
 * 用户业务服务实现
 * @author o0u0o
 * @date 2020/10/25 12:25 下午
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UsersMapper usersMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        Example userExample = new Example(Users.class);
        //类似于hibernate的条件
        Example.Criteria userCriteria = userExample.createCriteria();

        userCriteria.andEqualTo("username", username);

        Users result = usersMapper.selectOneByExample(userCriteria);

        return result == null ? false: true;
    }
}
