package com.wlj.sportgoods.sys.service.impl;

import com.wlj.sportgoods.sys.entity.User;
import com.wlj.sportgoods.sys.mapper.UserMapper;
import com.wlj.sportgoods.sys.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
