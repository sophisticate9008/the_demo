package com.wlj.sportgoods.sys.service.impl;

import com.wlj.sportgoods.sys.entity.Role;
import com.wlj.sportgoods.sys.mapper.RoleMapper;
import com.wlj.sportgoods.sys.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wlj
 * @since 2024-02-04
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    
    @Override
    public Map<String, Integer> getAllRolesAsMap() {
        List<Role> roles = this.list(new QueryWrapper<Role>().eq("available", 1)); // 查询所有 available = 1 的 Role
        return roles.stream()
                .collect(Collectors.toMap(Role::getName, Role::getId));
    }
    @Override
    public List<Integer> queryRolePermissionIdsByRid(Integer rid) {
        return this.getBaseMapper().queryRolePermissionIdsByRid(rid);
    }
}
