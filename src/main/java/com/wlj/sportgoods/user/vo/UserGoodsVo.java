package com.wlj.sportgoods.user.vo;

import com.wlj.sportgoods.user.entity.UserGoods;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
public class UserGoodsVo extends UserGoods{
    private Integer[] ids;
}
