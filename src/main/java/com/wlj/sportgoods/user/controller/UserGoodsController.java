package com.wlj.sportgoods.user.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wlj.sportgoods.sys.common.Constast;
import com.wlj.sportgoods.sys.common.DataGridView;
import com.wlj.sportgoods.sys.common.ResultObj;
import com.wlj.sportgoods.sys.common.WebUtils;
import com.wlj.sportgoods.sys.entity.User;
import com.wlj.sportgoods.sys.service.UserService;
import com.wlj.sportgoods.user.entity.Goods;
import com.wlj.sportgoods.user.entity.UserGoods;
import com.wlj.sportgoods.user.service.GoodsService;
import com.wlj.sportgoods.user.service.UserGoodsService;
import com.wlj.sportgoods.user.vo.UserGoodsVo;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wlj
 * @since 2024-02-07
 */
@RestController
@RequestMapping("/userGoods")
public class UserGoodsController {

    @Autowired
    private UserGoodsService userGoodsService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService userService;


    @RequestMapping("load")
    public DataGridView load(@RequestBody UserGoods userGoods) {
        User user = (User) WebUtils.getSession().getAttribute("user");
        QueryWrapper<UserGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", user.getAccount());
        queryWrapper.eq("status", userGoods.getStatus());
        List<UserGoods> result = userGoodsService.list(queryWrapper);
        return new DataGridView(result);
    }

    @RequestMapping("cartAdd")
    public ResultObj cartAdd(@RequestBody UserGoods userGoods) {
        User user = (User) WebUtils.getSession().getAttribute("user");
        userGoods.setAccount(user.getAccount());
        userGoods.setStatus(0);
        userGoods.setFinishTime(new Date());
        QueryWrapper<UserGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 0);
        queryWrapper.eq("account", userGoods.getAccount());
        queryWrapper.eq("gid", userGoods.getGid());
        UserGoods theUserGoods = userGoodsService.getOne(queryWrapper);
        if (theUserGoods != null) {
            theUserGoods.setNum(theUserGoods.getNum() + userGoods.getNum());
            theUserGoods.setFinishTime(new Date());
            if (userGoodsService.updateById(theUserGoods)) {
                return ResultObj.UPDATE_SUCCESS;
            } else {
                return ResultObj.UPDATE_ERROR;
            }
        } else {
            if (userGoodsService.save(userGoods)) {
                return ResultObj.ADD_SUCCESS;
            } else {
                return ResultObj.ADD_ERROR;
            }
        }

    }

    @RequestMapping("delete")
    public ResultObj delete(@RequestBody UserGoodsVo userGoodsVo) {
        User user = (User) WebUtils.getSession().getAttribute("user");
        Integer[] ids = userGoodsVo.getIds();
        userGoodsVo.setAccount(user.getAccount());

        for (int i = 0; i < ids.length; i++) {
            if (userGoodsService.getById(ids[i]).getId() != ids[i]) {
                return ResultObj.EXCEED_PERMISSION;
            }
            userGoodsService.removeById(ids[i]);
        }
        return ResultObj.DELETE_SUCCESS;

    }

    @Transactional
    @RequestMapping("buy")
    public ResultObj buy(@RequestBody UserGoods userGoods) {
        User user = (User) WebUtils.getSession().getAttribute("user");
        userGoods.setAccount(user.getAccount());
        if (userGoods.getId() != null) {
            userGoods = userGoodsService.getById(userGoods.getId());
            if (!userGoods.getAccount().equals(user.getAccount())) {
                return ResultObj.EXCEED_PERMISSION;
            }
        }
        Goods goods = goodsService.getById(userGoods.getGid());
        BigDecimal total = goods.getPrice().multiply(BigDecimal.valueOf(userGoods.getNum()));
        if (goods.getStock() < userGoods.getNum()) {
            return ResultObj.Nostock;
        }
        userGoods.setCost(total);
        userGoods.setFinishTime(new Date());
        userGoods.setStatus(1);
        user.setGold(user.getGold().subtract(total));
        userService.updateById(user);
        if (userGoodsService.saveOrUpdate(userGoods)) {
            return ResultObj.BUY_SUCCESS;
        } else {
            return ResultObj.BUY_ERROR;
        }
    }

    @Transactional
    @RequestMapping("refound")
    public ResultObj refound(@RequestBody UserGoods userGoods) {
        User user = (User) WebUtils.getSession().getAttribute("user");
        userGoods = userGoodsService.getById(userGoods.getId());
        if(!userGoods.getAccount().equals(user.getAccount())) {
            return ResultObj.EXCEED_PERMISSION;
        }
        userGoods.setStatus(-1);
        if (userGoodsService.updateById(userGoods)) {
            return ResultObj.OP_SUCCESS;
        } else {
            return ResultObj.OP_ERROR;
        }
    }

    @RequestMapping("getRefoundApply")
    public DataGridView getRefoundApply() {
        User user = (User) WebUtils.getSession().getAttribute("user");
        return new DataGridView(userGoodsService.getRefoundApplymentByAccount(user.getAccount()));
    }

    @Transactional
    @RequestMapping("agreeRefound")
    @RequiresPermissions({ "customerService:agreeRefound" })
    public ResultObj agreeRefound(@RequestBody UserGoods userGoods) {

        User user = (User) WebUtils.getSession().getAttribute("user");
        Goods goods = goodsService.getById(userGoods.getGid());
        UserGoods theUserGoods = userGoodsService.getById(userGoods.getId());
        if (theUserGoods.getStatus() != 1) {
            return new ResultObj(Constast.OK, "已被其他客服处理");
        }
        if (!goods.getMerchant().equals(user.getMerchant())) {
            return ResultObj.EXCEED_PERMISSION;
        } else {
            if (userGoods.getStatus() == 2) {
                userGoods.setCost(theUserGoods.getCost());
                user.setGold(user.getGold().add(userGoods.getCost()));
                userService.updateById(user);
                userGoods.setFinishTime(new Date());
                if (userGoodsService.updateById(userGoods)) {
                    return ResultObj.OP_SUCCESS;
                } else {
                    return ResultObj.OP_ERROR;
                }

            } else {
                userGoods.setFinishTime(new Date());
                if (userGoodsService.updateById(userGoods)) {
                    return ResultObj.OP_SUCCESS;
                } else {
                    return ResultObj.OP_ERROR;
                }
            }
        }
    }
    @RequestMapping("getSales")
    public DataGridView getSales(@RequestBody UserGoods userGoods) {
        return new DataGridView(userGoodsService.getSalesByGid(userGoods.getGid()));
    }


}
