package com.wlj.sportgoods.sys.service.impl;

import com.wlj.sportgoods.sys.common.ResultObj;
import com.wlj.sportgoods.sys.entity.User;
import com.wlj.sportgoods.sys.mapper.UserMapper;
import com.wlj.sportgoods.sys.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wlj
 * @since 2024-02-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public ResultObj register(User user) {
        User existingUser = userMapper.selectById(user.getAccount()); 
        if(existingUser != null) {
            return ResultObj.REGISTER_ERROR;
        }else {
            userMapper.insert(user);
            return ResultObj.REGISTER_SUCCESS;
        }
        
    }

}
