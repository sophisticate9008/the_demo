package com.wlj.sportgoods.sys.realm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wlj.sportgoods.sys.common.ActiverUser;
import com.wlj.sportgoods.sys.common.Constast;
import com.wlj.sportgoods.sys.entity.Permission;
import com.wlj.sportgoods.sys.entity.User;
import com.wlj.sportgoods.sys.service.PermissionService;
import com.wlj.sportgoods.sys.service.RoleService;
import com.wlj.sportgoods.sys.service.UserService;

public class UserRealm extends AuthorizingRealm{
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Override
    public String getName(){
        return this.getClass().getSimpleName();
    }
        
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        ActiverUser activerUser = (ActiverUser) principalCollection.getPrimaryPrincipal();
        User user = activerUser.getUser();
        List<String> superPermission = new ArrayList<>();
        superPermission.add("*:*");
        List<String> permissions = activerUser.getPermission();
        if (user.getType().equals(Constast.USER_TYPE_SUPER)){
            authorizationInfo.addStringPermissions(superPermission);
        }else {
            if (null!=permissions&&permissions.size()>0){
                authorizationInfo.addStringPermissions(permissions);
            }
        }
        return authorizationInfo;
    }
    

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account",authenticationToken.getPrincipal().toString());
        //通过用户名从数据库中查询出该用户
        User user = userService.getOne(queryWrapper);
        if (null!=user){
            ActiverUser activerUser = new ActiverUser();
            activerUser.setUser(user);

            //根据用户ID查询percode
            QueryWrapper<Permission> qw = new QueryWrapper<>();

            int rid = user.getType();
            //根据角色ID查询出权限ID
            List<Integer> pids = roleService.queryRolePermissionIdsByRid(rid);
            List<Permission> list = new ArrayList<>();
            if (pids.size()>0){
                qw.in("id",pids);
                list = permissionService.list(qw);
            }
            List<String> percodes = new ArrayList<>();
            List<String> menuList = new ArrayList<>();
            Map<String, String> menuUrls = new HashMap<>();
            Map<String, String> menuIcons = new HashMap<>();
            for (Permission permission : list) {
                String percode = permission.getPercode();
                String[] parts = percode.split(":");
                if(permission.getType().equals("permission")) {
                    percodes.add(percode);
                }
                if(permission.getType().equals("menu")) {
                    menuList.add(percode);
                    menuIcons.put(parts[2], permission.getIcon());
                    if(!permission.getHref().equals("")) {
                        menuUrls.put(parts[2], permission.getHref());
                    }
                }
                
            }
            activerUser.setMenuIcons(menuIcons);
            activerUser.setMenus(getMenuMap(menuList));
            //放到activerUser
            activerUser.setPermission(percodes);
            activerUser.setMenuUrls(menuUrls);
            if(user.getType() == 1) {
                activerUser.setHeadIcons(new ArrayList<>(Arrays.asList("star", "cart", "chat", "clock-history")));
            }else if (user.getType() == 2) {
                activerUser.setHeadIcons(new ArrayList<>(Arrays.asList("chat")));
            }else {

            }
            //生成盐
            ByteSource credentialsSalt=ByteSource.Util.bytes(user.getSalt());
            /**
             * 参数说明：
             * 参数1：活动的User
             * 参数2：从数据库里面查询出来的密码(已经通过MD5加密)
             * 参数3：从数据库里面查询出来的盐
             * 参数4：当前类名 
             */
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activerUser,user.getPassword(),credentialsSalt,this.getName());
            return info;
        }
        return null;
    }

    public Map<String,List<String>> getMenuMap(List<String> data) {
        Map<String, List<String>> menuMap = new HashMap<>();
        for (String line : data) {
            String[] parts = line.split(":");
            String key = parts[1];
            String value = parts[2];
            String flag = parts[3];
            if(flag.equals("1")) {
                menuMap.put(key, new ArrayList<>());
            }else {
                menuMap.computeIfAbsent(key, k -> new ArrayList<>());
                menuMap.get(key).add(value);
            }
        
        }
        return menuMap;
    }
}
