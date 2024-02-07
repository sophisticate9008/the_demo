package com.wlj.sportgoods.sys.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.wlj.sportgoods.sys.common.DataGridView;
import com.wlj.sportgoods.sys.service.RoleService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wlj
 * @since 2024-02-04
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @RequestMapping("getAllRole")
    public DataGridView getAllRole() {
        return new DataGridView(roleService.getAllRolesAsMap());
    }
}

