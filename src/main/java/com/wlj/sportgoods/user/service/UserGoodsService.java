package com.wlj.sportgoods.user.service;

import com.wlj.sportgoods.user.entity.UserGoods;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wlj
 * @since 2024-02-07
 */
public interface UserGoodsService extends IService<UserGoods> {
    /**
     * 判断用户是否购买了某个商品
     * @param account 用户账户
     * @param gid 商品ID
     * @return 如果用户购买了该商品返回true，否则返回false
     */
    boolean hasBoughtGoods(String account, Integer gid);
    List<UserGoods> getRefoundApplymentByAccount(String account);
    Integer getSalesByGid(Integer gid);
}
