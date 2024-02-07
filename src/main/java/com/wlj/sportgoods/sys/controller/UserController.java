package com.wlj.sportgoods.sys.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.wlj.sportgoods.sys.common.ResultObj;
import com.wlj.sportgoods.sys.entity.User;
import com.wlj.sportgoods.sys.service.UserService;
import com.wlj.sportgoods.sys.vo.UserVo;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wlj
 * @since 2024-02-05
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("register")
    public ResultObj register(User user) {
        return userService.register(user);
    }
}

