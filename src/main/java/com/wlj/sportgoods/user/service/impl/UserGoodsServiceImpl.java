package com.wlj.sportgoods.user.service.impl;

import com.wlj.sportgoods.user.entity.UserGoods;
import com.wlj.sportgoods.user.mapper.UserGoodsMapper;
import com.wlj.sportgoods.user.service.UserGoodsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wlj
 * @since 2024-02-07
 */
@Service
public class UserGoodsServiceImpl extends ServiceImpl<UserGoodsMapper, UserGoods> implements UserGoodsService {
    @Autowired
    private UserGoodsMapper userGoodsMapper;

    @Override
    public boolean hasBoughtGoods(String account, Integer gid) {
        QueryWrapper<UserGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        queryWrapper.eq("gid", gid);
        queryWrapper.ne("status", 0);
        int count = userGoodsMapper.selectCount(queryWrapper);
        return count > 0;  
    }

    @Override
    public List<UserGoods> getRefoundApplymentByAccount(String account) {
        return this.getBaseMapper().getRefoundApplymentByAccount(account);
    }

    @Override
    public Integer getSalesByGid(Integer gid) {
        return this.getBaseMapper().getSalesByGid(gid);
    }
    @Override
    public List<UserGoods> getOrdersByAccount(String account) {
        return this.getBaseMapper().getOrdersByAccount(account);
    }

}
