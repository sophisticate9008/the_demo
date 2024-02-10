package com.wlj.sportgoods.sys.service;

import com.wlj.sportgoods.sys.entity.Role;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wlj
 * @since 2024-02-04
 */
public interface RoleService extends IService<Role> {
    public Map<String, Integer> getAllRolesAsMap();
    public List<Integer> queryRolePermissionIdsByRid(Integer rid);
}
