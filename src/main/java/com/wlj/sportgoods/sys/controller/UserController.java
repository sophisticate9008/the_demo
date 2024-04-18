package com.wlj.sportgoods.sys.controller;


import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.wlj.sportgoods.sys.common.ActiverUser;
import com.wlj.sportgoods.sys.common.AppFileUtils;
import com.wlj.sportgoods.sys.common.DataGridView;
import com.wlj.sportgoods.sys.common.PasswordUtils;
import com.wlj.sportgoods.sys.common.ResultObj;
import com.wlj.sportgoods.sys.common.WebUtils;
import com.wlj.sportgoods.sys.entity.User;
import com.wlj.sportgoods.sys.service.RoleService;
import com.wlj.sportgoods.sys.service.UserService;
import com.wlj.sportgoods.sys.vo.UserVo;
import com.wlj.sportgoods.user.entity.Goods;

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
    @RequiresPermissions(value = {"merchant:createCustomerService"},logical = Logical.OR)
    public ResultObj createCustomer(@RequestBody User user) {
        User theUser = (User) WebUtils.getSession().getAttribute("user");
        if (theUser.getType() == 2) {
            user.setAvatarpath(AppFileUtils.renameFile(user.getAvatarpath()));
            user.setMerchant(theUser.getAccount());
            user.setType(3);
            if(!userService.register(user).equals(ResultObj.REGISTER_SUCCESS)) {
                AppFileUtils.removeFileByPath(user.getAvatarpath());
            };
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
        User user = (User) WebUtils.getSession().getAttribute("user");
        User userBasic = new User();
        User userIntact = user;
        userBasic.setAccount(userIntact.getAccount())
        .setGold(userIntact.getGold())
        .setType(userIntact.getType())
        .setMerchant(userIntact.getMerchant())
        .setNickname(userIntact.getNickname())
        .setAddress(userIntact.getAddress()) // 修正此处，使用 userIntact.getAddress()
        .setAvatarpath(userIntact.getAvatarpath())
        .setSex(userIntact.getSex());
        return new DataGridView(userBasic);
    }
    @RequestMapping("changeProfile")
    public ResultObj changeProfile(@RequestBody User user) {
        User auser = (User) WebUtils.getSession().getAttribute("user");
        user.setAccount(auser.getAccount());
        if(user.getAvatarpath().equals("")) {
            user.setAvatarpath(null);
        }else {
            user.setAvatarpath(AppFileUtils.renameFile(user.getAvatarpath()));            
        }

        if(userService.updateById(user)) {

            return ResultObj.UPDATE_SUCCESS;
        }else {
            AppFileUtils.removeFileByPath(user.getAvatarpath());
            return ResultObj.UPDATE_ERROR;
        }
    }
    @RequestMapping("changePassword")
    public ResultObj changePassword(@RequestBody JsonNode requestBody) {
        String oldPassword = requestBody.get("oldPassword").asText();
        String newPassword = requestBody.get("newPassword").asText();
        Subject subject = SecurityUtils.getSubject();
        ActiverUser activerUser = (ActiverUser) subject.getPrincipal();
        User user = activerUser.getUser();
        String oldHashPassword = PasswordUtils.hashPassword(oldPassword, user.getSalt());
        String newHashPassword = PasswordUtils.hashPassword(newPassword, user.getSalt());
        if(oldHashPassword.equals(user.getPassword())) {
            user.setPassword(newHashPassword);
            userService.updateById(user);
            subject.logout();
            return ResultObj.UPDATE_SUCCESS;
        }else {
            return ResultObj.UPDATE_ERROR;
        }
    }
    @RequestMapping("loadCustomerServices")
    public DataGridView loadCustomerService(@RequestBody UserVo userVo) {
        User user = (User) WebUtils.getSession().getAttribute("user");
        String merchant = user.getAccount();
        IPage<User> page = new Page<>(userVo.getPage(), userVo.getLimit());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchant", merchant);
        queryWrapper.like(StringUtils.isNotBlank(userVo.getAccount()), "account", userVo.getAccount());
        queryWrapper.like(StringUtils.isNotBlank(userVo.getNickname()), "nickname", userVo.getNickname());
        queryWrapper.orderByDesc("account");
        userService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }
    @RequestMapping("controlCustomerService")
    @RequiresPermissions({"merchant:controlCustomerService"})
    public ResultObj controlUser(@RequestBody UserVo userVo) {
        Subject subject = SecurityUtils.getSubject();
        ActiverUser activerUser = (ActiverUser) subject.getPrincipal();
        User targetUser = userService.getById(userVo.getAccount());
        if(targetUser.getMerchant().equals(activerUser.getUser().getAccount())) {
            if(userVo.getDelete()) {
                userService.removeById(userVo.getAccount());
                AppFileUtils.removeFileByPath(targetUser.getAvatarpath());
                return ResultObj.DELETE_SUCCESS;
            }else {
                userService.updateById(userVo);
                return ResultObj.UPDATE_SUCCESS;
            }
        }else {
            return ResultObj.UPDATE_ERROR;
        }        

    }
    @RequestMapping("getAllUser") 
    @RequiresPermissions({"*:*"})
    public DataGridView getAllUser(@RequestBody UserVo userVo) {
        IPage<User> page = new Page<>(userVo.getPage(), userVo.getLimit());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("account", "dereliction");
        queryWrapper.ne("type", "4");
        queryWrapper.like(StringUtils.isNotBlank(userVo.getAccount()), "account", userVo.getAccount());
        queryWrapper.eq(userVo.getAvailable()!= 5, "available", userVo.getAvailable());
        userService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }
    @RequestMapping("userManagement")
    @RequiresPermissions({"*:*"})
    public ResultObj userManagement(@RequestBody UserVo userVo) {
        if(userVo.getDelete()) {
            if(userService.removeById(userVo)) {
                return ResultObj.DELETE_SUCCESS;
            }else {
                return ResultObj.DELETE_ERROR;
            }
        }
        if(userService.updateById(userVo)) {
            return ResultObj.UPDATE_SUCCESS;
        }else {
            return ResultObj.UPDATE_ERROR;
        }
    }
    @RequestMapping("getUserBasicByAccount")
    public DataGridView getUserBasicByAccount(@RequestBody UserVo uservo) {
        User theUser = userService.getById(uservo.getAccount());
        User returnUser = new User();
        returnUser.setAvatarpath(theUser.getAvatarpath()).setNickname(theUser.getNickname()).setSex(theUser.getSex());
        return new DataGridView(returnUser);
    }



}

