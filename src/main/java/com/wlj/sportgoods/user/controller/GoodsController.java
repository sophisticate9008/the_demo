package com.wlj.sportgoods.user.controller;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlj.sportgoods.sys.common.ActiverUser;
import com.wlj.sportgoods.sys.common.AppFileUtils;
import com.wlj.sportgoods.sys.common.DataGridView;
import com.wlj.sportgoods.sys.common.ResultObj;
import com.wlj.sportgoods.user.entity.Goods;
import com.wlj.sportgoods.user.service.GoodsService;
import com.wlj.sportgoods.user.vo.GoodsVo;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wlj
 * @since 2024-02-27
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @RequestMapping("loadAllGoods")
    public DataGridView loadAllGoods(@RequestBody GoodsVo goodsVo) {
        if (!goodsVo.getShowInShop()) {
            Subject subject = SecurityUtils.getSubject();
            ActiverUser activerUser = (ActiverUser) subject.getPrincipal();
            if (goodsVo.getShowMygoods()) {
                goodsVo.setMerchant(activerUser.getUser().getAccount());
            }
        }

        IPage<Goods> page = new Page<>(goodsVo.getPage(), goodsVo.getLimit());
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(goodsVo.getGoodName() != null, "good_name", goodsVo.getGoodName());
        queryWrapper.eq(goodsVo.getId() != null, "id", goodsVo.getId());
        queryWrapper.eq(StringUtils.isNotBlank(goodsVo.getMerchant()), "merchant", goodsVo.getMerchant());
        queryWrapper.ne("merchant", "dereliction");
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getLabel()), "label", goodsVo.getLabel());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getIntroduction()), "introduction", goodsVo.getIntroduction());
        if (!goodsVo.getShowInShop()) {
            goodsService.page(page, queryWrapper);
            return new DataGridView(page.getTotal(), page.getRecords());
        } else {
            queryWrapper.ne("available", 0);
            goodsService.page(page, queryWrapper);
            List<Goods> records = page.getRecords();
            Collections.shuffle(records);
            return new DataGridView(page.getTotal(), records);
        }

    }

    @RequestMapping("addGoods")
    @RequiresPermissions({ "merchant:addGoods" })
    public ResultObj addGoods(@RequestBody Goods goods) {
        Subject subject = SecurityUtils.getSubject();
        ActiverUser activerUser = (ActiverUser) subject.getPrincipal();
        try {
            if (goods.getImagePath() != null && goods.getImagePath().endsWith("_temp")) {
                String newName = AppFileUtils.renameFile(goods.getImagePath());
                goods.setImagePath(newName);
            }
            goods.setMerchant(activerUser.getUser().getAccount());
            goods.setAvailable(1);
            if (!goodsService.save(goods)) {
                AppFileUtils.removeFileByPath(goods.getImagePath());
            }
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            AppFileUtils.removeFileByPath(goods.getImagePath());
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    @RequestMapping("updateGoods")
    @RequiresPermissions(value = { "merchant:updateGoods", "*:*" }, logical = Logical.OR)
    public ResultObj updateGoods(@RequestBody GoodsVo goodsVo) {
        String oldPath = goodsService.getById(goodsVo.getId()).getImagePath();
        Subject subject = SecurityUtils.getSubject();
        ActiverUser activerUser = (ActiverUser) subject.getPrincipal();
        Goods targetGood = goodsService.getById(goodsVo.getId());
        if (targetGood.getMerchant().equals(activerUser.getUser().getAccount())
                || activerUser.getUser().getType() == 4) {
            try {
                if (goodsVo.getDelete()) {
                    targetGood.setMerchant("dereliction");
                    targetGood.setAvailable(0);
                    goodsService.updateById(targetGood);
                    return ResultObj.DELETE_SUCCESS;
                }
                if (!(goodsVo.getImagePath() != null && goodsVo.getImagePath().equals("/images/noDefaultImage.jpg"))) {
                    if (goodsVo.getImagePath() != null && goodsVo.getImagePath().endsWith("_temp")) {
                        String newName = AppFileUtils.renameFile(goodsVo.getImagePath());
                        goodsVo.setImagePath(newName);
                        // 删除原先的图片
                        AppFileUtils.removeFileByPath(oldPath);
                    }
                }
                goodsService.updateById(goodsVo);
                return ResultObj.UPDATE_SUCCESS;
            } catch (Exception e) {
                AppFileUtils.removeFileByPath(oldPath);
                e.printStackTrace();
                return ResultObj.UPDATE_ERROR;
            }
        } else {
            return ResultObj.EXCEED_PERMISSION;
        }

    }

}
