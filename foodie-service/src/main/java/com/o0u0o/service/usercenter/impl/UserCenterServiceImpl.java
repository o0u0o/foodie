package com.o0u0o.service.usercenter.impl;

import com.o0u0o.mapper.UsersMapper;
import com.o0u0o.pojo.Users;
import com.o0u0o.pojo.bo.center.CenterUserBO;
import com.o0u0o.service.usercenter.UserCenterService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 用户中心业务实现
 * @author mac
 * @date 2020/12/3 3:01 下午
 */
@Service
public class UserCenterServiceImpl implements UserCenterService {

    @Autowired
    public UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfo(String userId) {
        Users user = usersMapper.selectByPrimaryKey(userId);
        user.setPassword(null);
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO) {
        Users updateUser = new Users();
        BeanUtils.copyProperties(centerUserBO, updateUser);
        updateUser.setId(userId);
        updateUser.setUpdatedTime(new Date());
        usersMapper.updateByPrimaryKeySelective(updateUser);

        return queryUserInfo(userId);
    }
}
