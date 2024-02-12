package com.wlj.sportgoods.sys.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wlj.sportgoods.sys.common.ActiverUser;
import com.wlj.sportgoods.sys.common.ResultObj;
import com.wlj.sportgoods.sys.entity.User;
import com.wlj.sportgoods.sys.service.RoleService;
import com.wlj.sportgoods.sys.service.UserService;

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

    @Autowired
    private RoleService roleService;

    @RequestMapping("register")
    public ResultObj register(@RequestBody User user) {
        if(roleService.getAllRolesAsMap().containsValue(user.getType())) {
            return userService.register(user);
        }else {
            return ResultObj.REGISTER_ERROR;
        }
    }
    @RequestMapping("createCustomerService")
    @RequiresPermissions(value = {"merchant:createCustomerService"},logical = Logical.AND)
    public ResultObj createCustomer(@RequestBody User user) {
        if (user.getType() == 3) {
            Subject subject = SecurityUtils.getSubject();
            ActiverUser activerUser = (ActiverUser) subject.getPrincipal();
            user.setMerchant(activerUser.getUser().getAccount());
            userService.register(user);
            return ResultObj.DISPATCH_SUCCESS;
        }else {
            return ResultObj.DISPATCH_ERROR;
        }
        
    }
    


}

