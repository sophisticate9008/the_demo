package com.wlj.sportgoods.sys.common;

import java.util.List;

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
}
