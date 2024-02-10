package com.wlj.sportgoods.sys.mapper;

import com.wlj.sportgoods.sys.entity.Role;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wlj
 * @since 2024-02-04
 */

public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据角色ID查询当前角色拥有的菜单ID和权限ID
     * @param rid
     * @return
     */
    List<Integer> queryRolePermissionIdsByRid(@Param("rid") Integer rid);
}
