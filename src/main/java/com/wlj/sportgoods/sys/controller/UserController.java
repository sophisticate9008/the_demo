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
import com.wlj.sportgoods.sys.common.DataGridView;
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
        Subject subject = SecurityUtils.getSubject();
        ActiverUser activerUser = (ActiverUser) subject.getPrincipal();
        if (activerUser.getUser().getType() == 3) {
            user.setMerchant(activerUser.getUser().getAccount());
            userService.register(user);
            return ResultObj.DISPATCH_SUCCESS;
        }else {
            return ResultObj.DISPATCH_ERROR;
        }
        
    }

    @RequestMapping("getMenu")
    public DataGridView getMenu() {
        Subject subject = SecurityUtils.getSubject();
        ActiverUser activerUser = (ActiverUser) subject.getPrincipal();
        return new DataGridView(activerUser.getMenus());
    }
    @RequestMapping("getMenuUrls")
    public DataGridView getMenuUrls() {
        Subject subject = SecurityUtils.getSubject();
        ActiverUser activerUser = (ActiverUser) subject.getPrincipal();
        return new DataGridView(activerUser.getMenuUrls());
    }
    @RequestMapping("getMenuIcons")
    public DataGridView getMenuIcons() {
        Subject subject = SecurityUtils.getSubject();
        ActiverUser activerUser = (ActiverUser) subject.getPrincipal();
        return new DataGridView(activerUser.getMenuIcons());
    }
    @RequestMapping("getHeadIcons")
    public DataGridView getHeadIcons() {
        Subject subject = SecurityUtils.getSubject();
        ActiverUser activerUser = (ActiverUser) subject.getPrincipal();
        return new DataGridView(activerUser.getHeadIcons());
    }
    @RequestMapping("getUserBasic")
    public DataGridView getUserBasic() {
        Subject subject = SecurityUtils.getSubject();
        ActiverUser activerUser = (ActiverUser) subject.getPrincipal();
        User userBasic = new User();
        User userIntact = activerUser.getUser();
        userBasic.setAccount(userIntact.getAccount())
        .setNickname(userIntact.getNickname())
        .setAddress(userIntact.getAddress()) // 修正此处，使用 userIntact.getAddress()
        .setAvatarpath(userIntact.getAvatarpath())
        .setSex(userIntact.getSex());
        return new DataGridView(userBasic);
    }
    @RequestMapping("changeProfile")
    public ResultObj changeProfile(@RequestBody User user) {
        Subject subject = SecurityUtils.getSubject();
        ActiverUser activerUser = (ActiverUser) subject.getPrincipal();
        user.setAccount(activerUser.getUser().getAccount());
        if(user.getAvatarpath().equals("")) {
            user.setAvatarpath(null);
        }
        if(userService.updateById(user)) {
            return ResultObj.UPDATE_SUCCESS;
        }else {
            return ResultObj.UPDATE_ERROR;
        }
        
    }
}

