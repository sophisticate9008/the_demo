package com.wlj.sportgoods.sys.common;

import java.util.List;
import java.util.Map;

import com.wlj.sportgoods.sys.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiverUser {
    private User user;
    private List<String> roles;
    private List<String> permission;
    private Map<String, List<String>> menus;
    private Map<String, String> menuUrls;
    private Map<String, String> menuIcons;
    private List<String> headIcons;
}
