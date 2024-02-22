package com.wlj.sportgoods.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("sys")
public class SystemController {
    @RequestMapping("toLogin")
    public String toLogin(){
        return "system/index/login";
    }
    @RequestMapping("toRegister")
    public String toRegister(){
        return "system/index/register";
    }
    @RequestMapping("createCustomerService")
    public String toCreateCustomerService() {
        return "merchant/createCustomerService";
    }
    @RequestMapping("index")
    public String toIndex(){
        return "system/index/index";
    }
    @RequestMapping("cropper")
    public String avataHandle() {
        return "system/tool/cropper.html";
    }
    @RequestMapping("changeProfile") 
    public String changeProfile() {
        return "user/changeProfile.html";
    }
    @RequestMapping("changePassword")
    public String changePassword() {
        return "user/changePassword.html";
    }
}
