package com.wlj.sportgoods.sys.service.impl;

import com.wlj.sportgoods.sys.common.PasswordUtils;
import com.wlj.sportgoods.sys.common.ResultObj;
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

    @Override
    public ResultObj register(User user) {
        User existingUser = this.getById(user.getAccount());
        if(existingUser != null) {
            return ResultObj.REGISTER_ERROR;
        }else if(user.getType() == 4) {
            return ResultObj.EXCEED_PERMISSION;
        }else {
            String salt = PasswordUtils.generateRandomSalt();
            user.setAccount(user.getAccount());
            user.setSalt(salt);
            user.setPassword(PasswordUtils.hashPassword(user.getPassword(), salt));
            if(user.getType() == 2) {
                user.setAvailable(0);
            }
            this.save(user);
            return ResultObj.REGISTER_SUCCESS;
        }
        
    }

}
