package com.wlj.sportgoods.user.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    public DataGridView load(@RequestBody UserGoodsVo userGoodsVo) {
        User user = (User) WebUtils.getSession().getAttribute("user");
        QueryWrapper<UserGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge(userGoodsVo.getStartTime() != null, "finishTime", userGoodsVo.getStartTime());
        queryWrapper.le(userGoodsVo.getEndTime() != null, "finishTime", userGoodsVo.getEndTime());
        queryWrapper.eq("account", user.getAccount());
        queryWrapper.eq(userGoodsVo.getStatus() != 5, "status", userGoodsVo.getStatus());
        queryWrapper.ne(userGoodsVo.getStatus() == 5, "status", 0);
        queryWrapper.eq(userGoodsVo.getId() != null, "id", userGoodsVo.getId());
        queryWrapper.orderByDesc("finishTime");
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
            if (!userGoodsService.getById(ids[i]).getAccount().equals(user.getAccount())) {
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
            UserGoods temp = userGoods;
            userGoods = userGoodsService.getById(userGoods.getId());
            userGoods.setAddress(temp.getAddress());
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
            goods.setStock(goods.getStock() - userGoods.getNum());
            goodsService.updateById(goods);
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
        if (!userGoods.getAccount().equals(user.getAccount())) {
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

    @RequestMapping("orderQuery")
    public DataGridView orderQuery() {
        User user = (User) WebUtils.getSession().getAttribute("user");
        if (user.getType() != 3) {
            return null;
        } else {
            return new DataGridView(userGoodsService.getOrdersByAccount(user.getAccount()));
        }
    }

    @Transactional
    @RequestMapping("agreeRefound")
    @RequiresPermissions({ "customerService:agreeRefound" })
    public ResultObj agreeRefound(@RequestBody UserGoods userGoods) {
        User user = (User) WebUtils.getSession().getAttribute("user");
        UserGoods theUserGoods = userGoodsService.getById(userGoods.getId());
        Goods goods = goodsService.getById(theUserGoods.getGid());
        if (theUserGoods.getStatus() != -1) {
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

    @RequestMapping("getSalesDataByGid")
    @RequiresPermissions({ "merchant:getSalesData" })
    public DataGridView getSalesDataByGid(@RequestBody UserGoodsVo userGoodsVo) {

        Map<Integer, List<Integer>> map = new ConcurrentHashMap<>();
        LocalDateTime startTime = userGoodsVo.getStartTime();
        LocalDateTime endTime = userGoodsVo.getEndTime();
        String unit = userGoodsVo.getUnit();
        for (Integer id : userGoodsVo.getIds()) {
            List<Integer> temp = new LinkedList<>();
            map.put(id, temp);
        }
        for (Integer id : userGoodsVo.getIds()) {
            LocalDateTime currentDate = startTime;
            while (!currentDate.isAfter(endTime)) {
                LocalDateTime nextDate;
                // 根据单位调整当前日期，比如按天、小时等
                switch (unit) {
                    case "day":
                        nextDate = currentDate.plusDays(1);
                        break;
                    case "hour":
                        nextDate = currentDate.plusHours(1);
                        break;
                    case "month":
                        nextDate = currentDate.plusMonths(1);
                        break;
                    case "year":
                        nextDate = currentDate.plusYears(1);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid unit: " + unit);
                }
                Integer res = userGoodsService.getSalesDataByGid(id, currentDate, nextDate);
                if(res!=null) {
                    map.get(id).add(res);
                }
                else {
                    map.get(id).add(0);
                }
                currentDate = nextDate;
            }
            Integer res = userGoodsService.getSalesDataByGid(id, currentDate,endTime);
            if(res!=null) {
                map.get(id).add(res);
            }
            else {
                map.get(id).add(0);
            }
        }
        return new DataGridView(map);
    }
}
